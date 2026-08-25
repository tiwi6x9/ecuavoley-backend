package com.spe.ecuavoley.controller;

import com.spe.ecuavoley.dto.ActualizarEstadoPartidoRequest;
import com.spe.ecuavoley.model.Partido;
import com.spe.ecuavoley.service.AdminAuthService;
import com.spe.ecuavoley.service.PartidoService;
import com.spe.ecuavoley.service.PartidoSseService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.spe.ecuavoley.dto.CrearPartidoProgramadoRequest;
import com.spe.ecuavoley.dto.HistorialPartidoResponse;
import com.spe.ecuavoley.dto.PartidoEnVivoResponse;
import com.spe.ecuavoley.dto.ProximoPartidoResponse;
import com.spe.ecuavoley.dto.ResultadoPartidoResponse;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

        private final PartidoService partidoService;
        private final PartidoSseService partidoSseService;
        private final AdminAuthService adminAuthService;

        public PartidoController(
                        PartidoService partidoService,
                        PartidoSseService partidoSseService,
                        AdminAuthService adminAuthService) {

                this.partidoService = partidoService;
                this.partidoSseService = partidoSseService;
                this.adminAuthService = adminAuthService;
        }

        // =========================================================
        // ENDPOINTS PROTEGIDOS
        // =========================================================

        @PostMapping
        public ResponseEntity<Partido> crearPartido(
                        @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
                        @RequestBody Partido partido) {

                if (!esAdministrador(authorization)) {
                        return ResponseEntity
                                        .status(401)
                                        .build();
                }

                Partido partidoCreado = partidoService.crearPartido(partido);

                return ResponseEntity.ok(partidoCreado);
        }

        @PutMapping("/{id}/estado")
        public ResponseEntity<Partido> actualizarEstado(
                        @PathVariable Long id,
                        @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
                        @RequestBody ActualizarEstadoPartidoRequest request) {

                if (!esAdministrador(authorization)) {
                        return ResponseEntity
                                        .status(401)
                                        .build();
                }

                return partidoService
                                .actualizarEstado(id, request)
                                .map(ResponseEntity::ok)
                                .orElseGet(
                                                () -> ResponseEntity.notFound().build());
        }

        // =========================================================
        // ENDPOINTS PUBLICOS
        // =========================================================

        @GetMapping("/{id}")
        public ResponseEntity<Partido> obtenerPartido(
                        @PathVariable Long id) {

                return partidoService
                                .obtenerPorId(id)
                                .map(ResponseEntity::ok)
                                .orElseGet(
                                                () -> ResponseEntity.notFound().build());
        }

        @GetMapping("/en-vivo")
        public ResponseEntity<List<PartidoEnVivoResponse>> obtenerPartidosEnVivo() {

                return ResponseEntity.ok(
                                partidoService.obtenerPartidosEnVivo());
        }

        @GetMapping(value = "/{id}/eventos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public SseEmitter escucharPartido(
                        @PathVariable Long id) {

                return partidoSseService.suscribirse(id);
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

        @PostMapping("/programados")
        public ResponseEntity<Partido> crearPartidoProgramado(
                        @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
                        @RequestBody CrearPartidoProgramadoRequest request) {

                if (!esAdministrador(authorization)) {
                        return ResponseEntity
                                        .status(401)
                                        .build();
                }

                return partidoService
                                .crearPartidoProgramado(request)
                                .map(ResponseEntity::ok)
                                .orElseGet(
                                                () -> ResponseEntity.badRequest().build());
        }

        @GetMapping("/campeonato/{campeonatoId}")
        public ResponseEntity<List<Partido>> obtenerPartidosPorCampeonato(
                        @PathVariable Long campeonatoId) {

                return ResponseEntity.ok(
                                partidoService.obtenerPorCampeonato(
                                                campeonatoId));
        }

        @GetMapping("/fecha/{fechaCampeonatoId}")
        public ResponseEntity<List<Partido>> obtenerPartidosPorFecha(
                        @PathVariable Long fechaCampeonatoId) {

                return ResponseEntity.ok(
                                partidoService.obtenerPorFecha(
                                                fechaCampeonatoId));
        }

        @PutMapping("/{id}/iniciar")
        public ResponseEntity<Partido> iniciarPartido(
                        @PathVariable Long id,
                        @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

                if (!esAdministrador(authorization)) {
                        return ResponseEntity
                                        .status(401)
                                        .build();
                }

                return partidoService
                                .iniciarPartido(id)
                                .map(ResponseEntity::ok)
                                .orElseGet(
                                                () -> ResponseEntity.notFound().build());
        }

        @GetMapping("/{id}/publico")
        public ResponseEntity<PartidoEnVivoResponse> obtenerDetallePublico(
                        @PathVariable Long id) {

                return partidoService
                                .obtenerDetallePublico(id)
                                .map(ResponseEntity::ok)
                                .orElseGet(
                                                () -> ResponseEntity.notFound().build());
        }

        @GetMapping("/campeonato/{campeonatoId}/resultados")
        public ResponseEntity<List<ResultadoPartidoResponse>> obtenerResultadosCampeonato(
                        @PathVariable Long campeonatoId) {

                return ResponseEntity.ok(
                                partidoService.obtenerResultadosCampeonato(
                                                campeonatoId));
        }

        @GetMapping("/proximos")
        public ResponseEntity<List<ProximoPartidoResponse>> obtenerProximosPartidos() {

                return ResponseEntity.ok(
                                partidoService
                                                .obtenerProximosPartidos());
        }

        @GetMapping("/campeonato/{campeonatoId}/proximos")
        public ResponseEntity<List<ProximoPartidoResponse>> obtenerProximosPartidosPorCampeonato(
                        @PathVariable Long campeonatoId) {

                return ResponseEntity.ok(
                                partidoService
                                                .obtenerProximosPartidosPorCampeonato(
                                                                campeonatoId));
        }

        @GetMapping("/historial")
        public ResponseEntity<List<HistorialPartidoResponse>> obtenerHistorial() {

                return ResponseEntity.ok(
                                partidoService.obtenerHistorial());
        }

}