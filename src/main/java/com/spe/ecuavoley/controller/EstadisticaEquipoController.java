package com.spe.ecuavoley.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.dto.EquipoEstadisticaResponse;
import com.spe.ecuavoley.dto.EquipoRankingResponse;
import com.spe.ecuavoley.dto.PartidoHistorialEquipoResponse;
import com.spe.ecuavoley.service.EstadisticaEquipoService;

@RestController
@RequestMapping("/api/estadisticas/equipos")
public class EstadisticaEquipoController {

    private final EstadisticaEquipoService estadisticaEquipoService;

    public EstadisticaEquipoController(
            EstadisticaEquipoService estadisticaEquipoService) {

        this.estadisticaEquipoService = estadisticaEquipoService;
    }

    @GetMapping("/{equipoId}")
    public ResponseEntity<EquipoEstadisticaResponse> obtenerEstadisticas(
            @PathVariable Long equipoId) {

        return estadisticaEquipoService
                .obtenerEstadisticas(equipoId)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build());
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<EquipoRankingResponse>> obtenerRanking() {

        return ResponseEntity.ok(
                estadisticaEquipoService
                        .obtenerRanking());
    }

    @GetMapping("/{equipoId}/historial")
    public ResponseEntity<List<PartidoHistorialEquipoResponse>> obtenerHistorial(
            @PathVariable Long equipoId) {

        return ResponseEntity.ok(
                estadisticaEquipoService
                        .obtenerHistorial(
                                equipoId));
    }

    @GetMapping("/campeonato/{campeonatoId}/ranking")
    public ResponseEntity<List<EquipoRankingResponse>> obtenerRankingPorCampeonato(
            @PathVariable Long campeonatoId) {

        return ResponseEntity.ok(
                estadisticaEquipoService
                        .obtenerRankingPorCampeonato(
                                campeonatoId));
    }
}