package com.example.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class KeycloakUser {

    private String username;
    private String firstName;
    private String lastName;

    private String email;
    private boolean enabled = true;

    private ArrayList<Credential> credentials;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ArrayList<Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(ArrayList<Credential> credentials) {
        this.credentials = credentials;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword(){
        return credentials.get(0).getValue();
    }

}
