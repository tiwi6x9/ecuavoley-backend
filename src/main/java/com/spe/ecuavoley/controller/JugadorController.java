package com.spe.ecuavoley.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.dto.JugadorResumenResponse;
import com.spe.ecuavoley.service.JugadorService;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(
            JugadorService jugadorService) {

        this.jugadorService = jugadorService;
    }

    @GetMapping(
            "/campeonato/{campeonatoId}/equipo/{equipoId}")
    public ResponseEntity<List<JugadorResumenResponse>>
            obtenerJugadoresEquipo(
                    @PathVariable Long campeonatoId,
                    @PathVariable Long equipoId) {

        return ResponseEntity.ok(
                jugadorService.obtenerJugadoresEquipo(
                        campeonatoId,
                        equipoId));
    }
}