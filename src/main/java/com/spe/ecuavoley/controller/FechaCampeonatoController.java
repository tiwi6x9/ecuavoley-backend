package com.spe.ecuavoley.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.model.FechaCampeonato;
import com.spe.ecuavoley.service.FechaCampeonatoService;

@RestController
@RequestMapping("/api/fechas")
public class FechaCampeonatoController {

    private final FechaCampeonatoService fechaCampeonatoService;

    public FechaCampeonatoController(
            FechaCampeonatoService fechaCampeonatoService) {

        this.fechaCampeonatoService =
                fechaCampeonatoService;
    }

    @GetMapping("/campeonato/{campeonatoId}")
    public ResponseEntity<List<FechaCampeonato>>
            obtenerPorCampeonato(
                    @PathVariable Long campeonatoId) {

        return ResponseEntity.ok(
                fechaCampeonatoService
                        .obtenerPorCampeonato(
                                campeonatoId));
    }
}