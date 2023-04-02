package com.example.userservice.model;

import org.springframework.stereotype.Component;

@Component
public class Credential {

    private String value;
    private String algorithm;
    private String type;
    private boolean isTemporary;


    public String getAlgorithm() {
        return "custom";
    }

    public String getType() {
        return "password";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTemporary() {
        return false;
    }

}
