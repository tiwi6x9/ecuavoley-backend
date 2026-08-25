package com.spe.ecuavoley.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    private final String adminCode;

    private String activeToken;

    public AdminAuthService(
            @Value("${ecuavoley.admin.code}") String adminCode) {

        this.adminCode = adminCode;
    }

    public String login(String codigo) {

        if (codigo == null || !adminCode.equals(codigo)) {
            return null;
        }

        activeToken = UUID.randomUUID().toString();

        return activeToken;
    }

    public boolean isValidToken(String token) {

        if (token == null || activeToken == null) {
            return false;
        }

        return activeToken.equals(token);
    }
}