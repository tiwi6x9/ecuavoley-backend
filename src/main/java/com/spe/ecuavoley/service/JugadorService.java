package com.spe.ecuavoley.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spe.ecuavoley.dto.JugadorResumenResponse;
import com.spe.ecuavoley.repository.EquipoJugadorRepository;

@Service
public class JugadorService {

    private final EquipoJugadorRepository equipoJugadorRepository;

    public JugadorService(
            EquipoJugadorRepository equipoJugadorRepository) {

        this.equipoJugadorRepository =
                equipoJugadorRepository;
    }

    public List<JugadorResumenResponse> obtenerJugadoresEquipo(
            Long campeonatoId,
            Long equipoId) {

        return equipoJugadorRepository
                .findByCampeonatoIdAndEquipoId(
                        campeonatoId,
                        equipoId)
                .stream()
                .map(relacion ->
                        new JugadorResumenResponse(
                                relacion.getJugador().getId(),
                                relacion.getJugador().getNombre(),
                                relacion.getJugador().getApodo(),
                                relacion.getJugador().getFotoUrl()))
                .toList();
    }
}