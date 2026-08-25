package com.spe.ecuavoley.dto;

public class AdminLoginResponse {

    private final String token;

    public AdminLoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}