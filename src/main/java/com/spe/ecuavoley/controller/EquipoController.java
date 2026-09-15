package com.spe.ecuavoley.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.dto.JugadorEquipoResponse;
import com.spe.ecuavoley.model.Equipo;
import com.spe.ecuavoley.model.Jugador;
import com.spe.ecuavoley.repository.DirigenteRepository;
import com.spe.ecuavoley.repository.EquipoJugadorRepository;
import com.spe.ecuavoley.repository.EquipoRepository;
import com.spe.ecuavoley.service.AdminAuthService;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoRepository equipoRepository;
    private final DirigenteRepository dirigenteRepository;
    private final EquipoJugadorRepository equipoJugadorRepository;
    private final AdminAuthService adminAuthService;

    public EquipoController(
            EquipoRepository equipoRepository,
            DirigenteRepository dirigenteRepository,
            EquipoJugadorRepository equipoJugadorRepository,
            AdminAuthService adminAuthService) {

        this.equipoRepository = equipoRepository;
        this.dirigenteRepository = dirigenteRepository;
        this.equipoJugadorRepository = equipoJugadorRepository;
        this.adminAuthService = adminAuthService;
    }

    // =========================================================
    // JUGADORES DEL EQUIPO (SIN IMPORTAR EL CAMPEONATO)
    // =========================================================
    //
    // La pantalla de "Rankings" global (fuera de un campeonato
    // específico) no tiene un campeonatoId para pedir la nómina
    // con /api/campeonatos/{campeonatoId}/equipos/{equipoId}/jugadores,
    // así que aquí se listan los jugadores de un equipo a través
    // de TODOS los campeonatos en los que ha jugado, sin repetir
    // al mismo jugador si aparece en más de uno.
    @GetMapping("/{equipoId}/jugadores")
    public ResponseEntity<List<JugadorEquipoResponse>> obtenerJugadores(
            @PathVariable Long equipoId) {

        Map<Long, Jugador> jugadoresPorId = new LinkedHashMap<>();

        for (var equipoJugador : equipoJugadorRepository
                .findByEquipoId(equipoId)) {

            Jugador jugador = equipoJugador.getJugador();

            if (jugador != null) {
                jugadoresPorId.putIfAbsent(
                        jugador.getId(),
                        jugador);
            }
        }

        List<JugadorEquipoResponse> jugadores = jugadoresPorId
                .values()
                .stream()
                .map(jugador -> new JugadorEquipoResponse(
                        jugador.getId(),
                        jugador.getNombre(),
                        jugador.getFotoUrl(),
                        jugador.getPosicion()))
                .toList();

        return ResponseEntity.ok(jugadores);
    }

    @PutMapping("/{equipoId}/dirigente/{dirigenteId}")
    public ResponseEntity<Void> asignarDirigente(
            @PathVariable Long equipoId,
            @PathVariable Long dirigenteId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        if (!esAdministrador(authorization)) {
            return ResponseEntity
                    .status(401)
                    .build();
        }

        var equipoOpt = equipoRepository.findById(equipoId);

        var dirigenteOpt = dirigenteRepository.findById(dirigenteId);

        if (equipoOpt.isEmpty()
                || dirigenteOpt.isEmpty()) {

            return ResponseEntity.notFound().build();
        }

        Equipo equipo = equipoOpt.get();

        equipo.setDirigente(
                dirigenteOpt.get());

        equipoRepository.save(equipo);

        return ResponseEntity.ok().build();
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
