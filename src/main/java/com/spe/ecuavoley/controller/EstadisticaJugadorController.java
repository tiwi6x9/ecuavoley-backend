package com.spe.ecuavoley.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.dto.JugadorEstadisticaResponse;
import com.spe.ecuavoley.dto.JugadorRankingResponse;
import com.spe.ecuavoley.dto.PartidoHistorialJugadorResponse;
import com.spe.ecuavoley.service.EstadisticaJugadorService;

@RestController
@RequestMapping("/api/estadisticas/jugadores")
public class EstadisticaJugadorController {

    private final EstadisticaJugadorService estadisticaJugadorService;

    public EstadisticaJugadorController(
            EstadisticaJugadorService estadisticaJugadorService) {

        this.estadisticaJugadorService = estadisticaJugadorService;
    }

    @GetMapping("/{jugadorId}")
    public ResponseEntity<JugadorEstadisticaResponse> obtenerEstadisticas(
            @PathVariable Long jugadorId) {

        return estadisticaJugadorService
                .obtenerEstadisticas(jugadorId)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build());
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<JugadorRankingResponse>> obtenerRanking() {

        return ResponseEntity.ok(
                estadisticaJugadorService
                        .obtenerRanking());
    }

    @GetMapping("/{jugadorId}/historial")
    public ResponseEntity<List<PartidoHistorialJugadorResponse>> obtenerHistorial(
            @PathVariable Long jugadorId) {

        return ResponseEntity.ok(
                estadisticaJugadorService
                        .obtenerHistorial(
                                jugadorId));
    }

    @GetMapping("/campeonato/{campeonatoId}/ranking")
    public ResponseEntity<List<JugadorRankingResponse>> obtenerRankingPorCampeonato(
            @PathVariable Long campeonatoId) {

        return ResponseEntity.ok(
                estadisticaJugadorService
                        .obtenerRankingPorCampeonato(
                                campeonatoId));
    }
}