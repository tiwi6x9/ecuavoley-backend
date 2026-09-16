package com.spe.ecuavoley.controller;

import com.spe.ecuavoley.dto.AdminLoginRequest;
import com.spe.ecuavoley.dto.AdminLoginResponse;
import com.spe.ecuavoley.service.AdminAuthService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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
            @Valid @RequestBody AdminLoginRequest request) {

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

    // =========================================================
    // VERIFICAR SESIÓN
    // =========================================================
    //
    // La app guarda el token de administrador en el dispositivo
    // para no pedir el código cada vez que se abre la app. Antes,
    // la pantalla de administración se mostraba con solo revisar
    // que hubiera ALGÚN token guardado localmente, sin confirmar
    // con el servidor que siguiera siendo válido — así que un
    // token viejo, de una sesión ya cerrada (por ejemplo, tras
    // reiniciar el backend, lo que borra la sesión activa en
    // memoria) seguía abriendo la pantalla de administración sin
    // pedir el código de nuevo. Este endpoint permite a la app
    // confirmar, antes de mostrar esa pantalla, que el token
    // guardado sigue siendo el vigente.
    @GetMapping("/verify")
    public ResponseEntity<Void> verificar(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        String token = extraerToken(authorization);

        if (token == null || !adminAuthService.isValidToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        return ResponseEntity.ok().build();
    }

    private String extraerToken(String authorization) {

        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {

            return null;
        }

        return authorization.substring(7).trim();
    }
}
