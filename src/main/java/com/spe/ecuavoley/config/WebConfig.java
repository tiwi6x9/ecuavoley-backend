package com.spe.ecuavoley.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// =========================================================
// CORS PARA LA VERSION WEB
// =========================================================
//
// La app hoy solo corre en Android, donde CORS no aplica (es una
// regla que solo respetan los navegadores, nunca los clientes HTTP
// nativos). Al preparar una version web de la app (Flutter Web),
// el navegador SI va a exigir que este backend le indique
// explicitamente que dominios tienen permiso para llamarlo.
//
// Esta configuracion lee la lista de dominios permitidos desde la
// variable de entorno CORS_ALLOWED_ORIGINS (separados por comas),
// exactamente el mismo patron que ya usamos para ADMIN_CODE: si la
// variable no esta definida, la lista queda vacia y no se registra
// ningun mapeo de CORS, asi que esto no tiene ningun efecto sobre
// nada que ya este funcionando (ni la app Android, ni el resto del
// backend) hasta que se configure explicitamente en Railway.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<String> allowedOrigins;

    public WebConfig(
            @Value("${ecuavoley.cors.allowed-origins:}") String allowedOriginsRaw) {

        this.allowedOrigins = parseOrigenes(allowedOriginsRaw);
    }

    private static List<String> parseOrigenes(String allowedOriginsRaw) {

        if (allowedOriginsRaw == null || allowedOriginsRaw.isBlank()) {
            return List.of();
        }

        return Arrays.stream(allowedOriginsRaw.split(","))
                .map(String::trim)
                .filter(origen -> !origen.isEmpty())
                .toList();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        if (allowedOrigins.isEmpty()) {
            return;
        }

        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins.toArray(new String[0]))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
