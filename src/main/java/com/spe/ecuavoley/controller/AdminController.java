package com.spe.ecuavoley.controller;

import com.spe.ecuavoley.dto.AdminLoginRequest;
import com.spe.ecuavoley.dto.AdminLoginResponse;
import com.spe.ecuavoley.service.AdminAuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminAuthService adminAuthService;

    public AdminController(
            AdminAuthService adminAuthService) {

        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(
            @RequestBody AdminLoginRequest request) {

        String token = adminAuthService.login(
                request.getCodigo());

        if (token == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        return ResponseEntity.ok(
                new AdminLoginResponse(token));
    }
}