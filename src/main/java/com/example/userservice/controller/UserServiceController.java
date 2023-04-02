package com.example.userservice.controller;


import com.example.userservice.model.AuthToken;
import com.example.userservice.model.KeycloakUser;
import com.example.userservice.model.LoginRequest;
import com.example.userservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserServiceController {

    @Autowired
    private AuthService authService;


    @GetMapping("/check")
    public String check(){
        return "Api is working";
    }

    @PostMapping("/login")
    public AuthToken loginUser(@RequestBody LoginRequest loginRequest){
        try {
            return authService.login(loginRequest.getUsername(), loginRequest.getPassword()).getBody();
        } catch(HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString(), e);
        }
    }

    @PostMapping("/register")
    public AuthToken createUser(@RequestBody KeycloakUser keycloakUser){
        try {
            return authService.createUser(keycloakUser).getBody();
        } catch(HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString(), e);
        }
    }

}
