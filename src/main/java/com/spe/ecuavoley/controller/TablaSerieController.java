package com.spe.ecuavoley.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.dto.TablaSerieResponse;
import com.spe.ecuavoley.service.TablaSerieService;

@RestController
@RequestMapping("/api/series")
public class TablaSerieController {

    private final TablaSerieService tablaSerieService;

    public TablaSerieController(
            TablaSerieService tablaSerieService) {

        this.tablaSerieService = tablaSerieService;
    }

    @GetMapping("/{serieId}/tabla")
    public ResponseEntity<List<TablaSerieResponse>> obtenerTabla(
            @PathVariable Long serieId) {

        return ResponseEntity.ok(
                tablaSerieService
                        .obtenerTabla(
                                serieId));
    }
}