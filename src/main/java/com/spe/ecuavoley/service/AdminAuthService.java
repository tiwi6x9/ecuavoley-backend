package com.spe.ecuavoley.service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    // Cuánto dura una sesión de administrador antes de pedir el
    // código de nuevo, aunque el teléfono siga teniendo el token
    // guardado. Antes no expiraba nunca por tiempo (solo se
    // invalidaba si alguien más iniciaba sesión, o si el backend
    // se reiniciaba) — ahora, pasado este tiempo, isValidToken()
    // la trata como vencida sin necesidad de que pase ninguna de
    // esas dos cosas.
    private static final Duration DURACION_SESION = Duration.ofDays(2);

    private final String adminCode;

    private String activeToken;
    private Instant activeTokenExpiracion;

    public AdminAuthService(
            @Value("${ecuavoley.admin.code}") String adminCode) {

        this.adminCode = adminCode;
    }

    public String login(String codigo) {

        if (codigo == null || !adminCode.equals(codigo)) {
            return null;
        }

        activeToken = UUID.randomUUID().toString();
        activeTokenExpiracion = Instant.now().plus(DURACION_SESION);

        return activeToken;
    }

    public boolean isValidToken(String token) {

        if (token == null || activeToken == null) {
            return false;
        }

        if (activeTokenExpiracion == null ||
                Instant.now().isAfter(activeTokenExpiracion)) {

            return false;
        }

        return activeToken.equals(token);
    }
}
