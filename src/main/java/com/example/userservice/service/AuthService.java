package com.example.userservice.service;

import com.example.userservice.model.AuthToken;
import com.example.userservice.model.KeycloakUser;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.json.Json;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private AuthToken adminToken;
    @Autowired
    private Environment env;

    private AuthToken getAdminToken(){
        if (adminToken == null || !adminToken.isValid()) {
            String baseUrl = env.getProperty("keycloak.auth-server-url");
            String realm = env.getProperty("keycloak.realm");
            String uri = baseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", env.getProperty("keycloak.resource"));
            map.add("client_secret", env.getProperty("keycloak.credentials.secret"));
            map.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            adminToken = restTemplate.postForObject(uri, request, AuthToken.class);
            if (adminToken != null) {
                adminToken.setValid(true);
            }
        }
        return adminToken;
    }

    private void saveUserInDb(String sid, KeycloakUser keycloakUser){
        User user = new User();
        user.setId(sid);
        user.setName(keycloakUser.getFirstName() + " " + keycloakUser.getLastName());
        user.setEmail(keycloakUser.getEmail());
        userRepository.save(user);
    }


    private String getSid(String accessToken){
        String[] chunks = accessToken.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        return new Gson().fromJson(payload, JsonObject.class).get("sid").getAsString();
    }


    public ResponseEntity<AuthToken> createUser(KeycloakUser user) throws HttpStatusCodeException {
        String baseUrl = env.getProperty("keycloak.auth-server-url");
        String realm = env.getProperty("keycloak.realm");
        String uri = baseUrl + "/admin/realms/" + realm + "/users";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + getAdminToken().getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<KeycloakUser> request = new HttpEntity<>(user, headers);
        restTemplate.postForEntity(uri, request, String.class);
        ResponseEntity<AuthToken>  response = login(user.getUsername(), user.getPassword());
        String sid = getSid(response.getBody().getAccessToken());
        saveUserInDb(sid, user);
        return response;
    }


    public ResponseEntity<AuthToken> login(String userName, String password){
        String baseUrl = env.getProperty("keycloak.auth-server-url");
        String realm = env.getProperty("keycloak.realm");
        String uri = baseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", env.getProperty("keycloak.resource"));
        map.add("client_secret", env.getProperty("keycloak.credentials.secret"));
        map.add("grant_type", "password");
        map.add("username", userName);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return restTemplate.postForEntity(uri, request, AuthToken.class);
    }


    public User getUser(String token){
        String sid = getSid(token);
        return userRepository.findById(sid).get();
    }


}
