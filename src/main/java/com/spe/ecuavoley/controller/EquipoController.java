package com.spe.ecuavoley.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spe.ecuavoley.model.Equipo;
import com.spe.ecuavoley.repository.DirigenteRepository;
import com.spe.ecuavoley.repository.EquipoRepository;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoRepository equipoRepository;
    private final DirigenteRepository dirigenteRepository;

    public EquipoController(
            EquipoRepository equipoRepository,
            DirigenteRepository dirigenteRepository) {

        this.equipoRepository = equipoRepository;
        this.dirigenteRepository = dirigenteRepository;
    }

    @PutMapping("/{equipoId}/dirigente/{dirigenteId}")
    public ResponseEntity<Void> asignarDirigente(
            @PathVariable Long equipoId,
            @PathVariable Long dirigenteId) {

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
    
}
