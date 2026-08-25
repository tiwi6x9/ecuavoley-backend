package com.spe.ecuavoley.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.dto.JugadorResumenResponse;
import com.spe.ecuavoley.dto.SeleccionarJugadoresPartidoRequest;
import com.spe.ecuavoley.dto.SeleccionarMvpRequest;
import com.spe.ecuavoley.service.AdminAuthService;
import com.spe.ecuavoley.service.PartidoJugadorService;


@RestController
@RequestMapping("/api/partidos")
public class PartidoJugadorController {

    private final PartidoJugadorService partidoJugadorService;
    private final AdminAuthService adminAuthService;

    public PartidoJugadorController(
            PartidoJugadorService partidoJugadorService,
            AdminAuthService adminAuthService) {

        this.partidoJugadorService = partidoJugadorService;

        this.adminAuthService = adminAuthService;
    }

    @PutMapping("/{partidoId}/jugadores")
    public ResponseEntity<Void> seleccionarJugadores(
            @PathVariable Long partidoId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @RequestBody SeleccionarJugadoresPartidoRequest request) {

        if (!esAdministrador(authorization)) {
            return ResponseEntity
                    .status(401)
                    .build();
        }

        boolean actualizado = partidoJugadorService
                .seleccionarJugadores(
                        partidoId,
                        request);

        if (!actualizado) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{partidoId}/jugadores")
    public ResponseEntity<List<JugadorResumenResponse>> obtenerJugadores(
            @PathVariable Long partidoId) {

        return ResponseEntity.ok(
                partidoJugadorService
                        .obtenerJugadoresPartido(
                                partidoId));
    }

    private boolean esAdministrador(
            String authorization) {

        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {

            return false;
        }

        String token = authorization.substring(7).trim();

        return adminAuthService
                .isValidToken(token);
    }

    @PutMapping("/{partidoId}/mvp")
    public ResponseEntity<Void> seleccionarMvp(
            @PathVariable Long partidoId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @RequestBody SeleccionarMvpRequest request) {

        if (!esAdministrador(authorization)) {
            return ResponseEntity
                    .status(401)
                    .build();
        }

        boolean actualizado = partidoJugadorService
                .seleccionarMvp(
                        partidoId,
                        request.getJugadorId());

        if (!actualizado) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        return ResponseEntity.noContent().build();
    }
}