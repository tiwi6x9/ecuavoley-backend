package com.spe.ecuavoley.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.model.Dirigente;
import com.spe.ecuavoley.repository.DirigenteRepository;
import com.spe.ecuavoley.service.AdminAuthService;

@RestController
@RequestMapping("/api/dirigentes")
public class DirigenteController {

    private final DirigenteRepository dirigenteRepository;
    private final AdminAuthService adminAuthService;

    public DirigenteController(
            DirigenteRepository dirigenteRepository,
            AdminAuthService adminAuthService) {

        this.dirigenteRepository = dirigenteRepository;
        this.adminAuthService = adminAuthService;
    }

    // =========================================================
    // ENDPOINTS PUBLICOS
    // =========================================================

    @GetMapping
    public List<Dirigente> listar() {
        return dirigenteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dirigente> obtener(
            @PathVariable Long id) {

        return dirigenteRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // =========================================================
    // ENDPOINTS PROTEGIDOS
    // =========================================================

    @PostMapping
    public ResponseEntity<Dirigente> crear(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @RequestBody Dirigente dirigente) {

        if (!esAdministrador(authorization)) {
            return ResponseEntity
                    .status(401)
                    .build();
        }

        Dirigente guardado = dirigenteRepository.save(dirigente);

        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dirigente> actualizar(
            @PathVariable Long id,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @RequestBody Dirigente datos) {

        if (!esAdministrador(authorization)) {
            return ResponseEntity
                    .status(401)
                    .build();
        }

        return dirigenteRepository
                .findById(id)
                .map(dirigente -> {

                    dirigente.setNombre(
                            datos.getNombre());

                    dirigente.setTelefono(
                            datos.getTelefono());

                    dirigente.setFotoUrl(
                            datos.getFotoUrl());

                    return ResponseEntity.ok(
                            dirigenteRepository.save(dirigente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // =========================================================
    // AUTORIZACION
    // =========================================================

    private boolean esAdministrador(
            String authorization) {

        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {

            return false;
        }

        String token = authorization
                .substring(7)
                .trim();

        return adminAuthService
                .isValidToken(token);
    }
}