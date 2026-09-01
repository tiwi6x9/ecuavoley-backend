package com.spe.ecuavoley.service;

import com.spe.ecuavoley.dto.EquipoCampeonatoResponse;
import com.spe.ecuavoley.dto.JugadorEquipoResponse;
import com.spe.ecuavoley.model.Campeonato;
import com.spe.ecuavoley.repository.CampeonatoRepository;
import com.spe.ecuavoley.repository.EquipoJugadorRepository;
import com.spe.ecuavoley.repository.SerieEquipoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final EquipoJugadorRepository equipoJugadorRepository;
    private final SerieEquipoRepository serieEquipoRepository;

    public CampeonatoService(
            CampeonatoRepository campeonatoRepository,
            EquipoJugadorRepository equipoJugadorRepository,
            SerieEquipoRepository serieEquipoRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.equipoJugadorRepository = equipoJugadorRepository;
        this.serieEquipoRepository = serieEquipoRepository;
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
                            jugador.getFotoUrl(),
                            jugador.getPosicion());
                })
                .toList();
    }

    public List<EquipoCampeonatoResponse> obtenerEquiposCampeonato(
            Long campeonatoId) {

        return serieEquipoRepository
                .findBySerieCampeonatoId(campeonatoId)
                .stream()
                .map(serieEquipo -> serieEquipo.getEquipo())
                .distinct()
                .map(equipo -> {

                    var dirigente = equipo.getDirigente();

                    return new EquipoCampeonatoResponse(
                            equipo.getId(),
                            equipo.getNombre(),
                            equipo.getLogoUrl(),
                            dirigente != null ? dirigente.getId() : null,
                            dirigente != null ? dirigente.getNombre() : null,
                            dirigente != null ? dirigente.getTelefono() : null,
                            dirigente != null ? dirigente.getFotoUrl() : null);
                })
                .toList();
    }
}