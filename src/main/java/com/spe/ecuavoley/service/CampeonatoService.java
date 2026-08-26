package com.spe.ecuavoley.service;

import com.spe.ecuavoley.dto.JugadorEquipoResponse;
import com.spe.ecuavoley.model.Campeonato;
import com.spe.ecuavoley.repository.CampeonatoRepository;
import com.spe.ecuavoley.repository.EquipoJugadorRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final EquipoJugadorRepository equipoJugadorRepository;

    public CampeonatoService(
            CampeonatoRepository campeonatoRepository,
            EquipoJugadorRepository equipoJugadorRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.equipoJugadorRepository = equipoJugadorRepository;
    }

    public List<Campeonato> obtenerTodos() {
        return campeonatoRepository.findAll();
    }

    public Optional<Campeonato> obtenerPorId(Long id) {
        return campeonatoRepository.findById(id);
    }

    public List<JugadorEquipoResponse> obtenerJugadoresEquipo(
            Long campeonatoId,
            Long equipoId) {

        return equipoJugadorRepository
                .findByCampeonatoIdAndEquipoId(
                        campeonatoId,
                        equipoId)
                .stream()
                .map(equipoJugador -> {

                    var jugador = equipoJugador.getJugador();

                    return new JugadorEquipoResponse(
                            jugador.getId(),
                            jugador.getNombre(),
                            jugador.getFotoUrl());
                })
                .toList();
    }
}