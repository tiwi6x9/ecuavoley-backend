package com.spe.ecuavoley.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.model.Equipo;
import com.spe.ecuavoley.repository.DirigenteRepository;
import com.spe.ecuavoley.repository.EquipoRepository;
import com.spe.ecuavoley.service.AdminAuthService;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoRepository equipoRepository;
    private final DirigenteRepository dirigenteRepository;
    private final AdminAuthService adminAuthService;

    public EquipoController(
            EquipoRepository equipoRepository,
            DirigenteRepository dirigenteRepository,
            AdminAuthService adminAuthService) {

        this.equipoRepository = equipoRepository;
        this.dirigenteRepository = dirigenteRepository;
        this.adminAuthService = adminAuthService;
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
