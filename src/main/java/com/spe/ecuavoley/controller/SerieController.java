package com.spe.ecuavoley.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.dto.SerieResumenResponse;
import com.spe.ecuavoley.service.SerieService;

@RestController
@RequestMapping("/api/campeonatos")
public class SerieController {

    private final SerieService serieService;

    public SerieController(
            SerieService serieService) {

        this.serieService = serieService;
    }

    @GetMapping("/{campeonatoId}/series")
    public ResponseEntity<List<SerieResumenResponse>> obtenerSeries(
            @PathVariable Long campeonatoId) {

        return ResponseEntity.ok(
                serieService
                        .obtenerPorCampeonato(
                                campeonatoId));
    }
}