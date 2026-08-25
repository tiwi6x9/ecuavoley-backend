package com.spe.ecuavoley.controller;

import com.spe.ecuavoley.model.Campeonato;
import com.spe.ecuavoley.service.CampeonatoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos")
public class CampeonatoController {

    private final CampeonatoService campeonatoService;

    public CampeonatoController(
            CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    @GetMapping
    public ResponseEntity<List<Campeonato>> obtenerTodos() {
        return ResponseEntity.ok(
                campeonatoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campeonato> obtenerPorId(
            @PathVariable Long id) {

        return campeonatoService
                .obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }
}