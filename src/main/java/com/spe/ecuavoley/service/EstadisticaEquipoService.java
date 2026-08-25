package com.spe.ecuavoley.service;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.spe.ecuavoley.dto.EquipoEstadisticaResponse;
import com.spe.ecuavoley.dto.EquipoRankingResponse;
import com.spe.ecuavoley.dto.PartidoHistorialEquipoResponse;
import com.spe.ecuavoley.model.Equipo;
import com.spe.ecuavoley.model.Partido;
import com.spe.ecuavoley.repository.EquipoRepository;
import com.spe.ecuavoley.repository.PartidoRepository;

@Service
public class EstadisticaEquipoService {

    private final EquipoRepository equipoRepository;
    private final PartidoRepository partidoRepository;

    public EstadisticaEquipoService(
            EquipoRepository equipoRepository,
            PartidoRepository partidoRepository) {

        this.equipoRepository = equipoRepository;
        this.partidoRepository = partidoRepository;
    }

    public Optional<EquipoEstadisticaResponse> obtenerEstadisticas(Long equipoId) {

        Optional<Equipo> equipoOpt = equipoRepository.findById(equipoId);

        if (equipoOpt.isEmpty()) {
            return Optional.empty();
        }

        Equipo equipo = equipoOpt.get();

        List<Partido> partidos = partidoRepository
                .findPartidosFinalizadosByEquipoId(
                        equipoId);

        long jugados = partidos.size();

        long ganados = 0;
        long perdidos = 0;

        long setsGanados = 0;
        long setsPerdidos = 0;

        for (Partido partido : partidos) {

            boolean esEquipoA = partido.getEquipoAEntidad()
                    .getId()
                    .equals(equipoId);

            if (esEquipoA) {

                setsGanados += partido.getSetsA();
                setsPerdidos += partido.getSetsB();

            } else {

                setsGanados += partido.getSetsB();
                setsPerdidos += partido.getSetsA();
            }

            if (partido.getEquipoGanador() == null) {
                continue;
            }

            if (partido
                    .getEquipoGanador()
                    .getId()
                    .equals(equipoId)) {

                ganados++;

            } else {

                perdidos++;
            }
        }

        double porcentaje = jugados == 0
                ? 0
                : (ganados * 100.0) / jugados;

        EquipoEstadisticaResponse response = new EquipoEstadisticaResponse();

        response.setEquipoId(equipo.getId());
        response.setNombre(equipo.getNombre());

        response.setPartidosJugados(jugados);
        response.setPartidosGanados(ganados);
        response.setPartidosPerdidos(perdidos);

        response.setSetsGanados(setsGanados);
        response.setSetsPerdidos(setsPerdidos);
        response.setLogoUrl(equipo.getLogoUrl());

        response.setPorcentajeVictorias(
                Math.round(porcentaje * 100.0)
                        / 100.0);

        return Optional.of(response);
    }

    public List<EquipoRankingResponse> obtenerRanking() {

        return equipoRepository.findAll()
                .stream()
                .map(equipo -> {

                    EquipoEstadisticaResponse estadistica = obtenerEstadisticas(
                            equipo.getId())
                            .orElseThrow();

                    EquipoRankingResponse ranking = new EquipoRankingResponse();

                    ranking.setEquipoId(
                            estadistica.getEquipoId());

                    ranking.setNombre(
                            estadistica.getNombre());

                    ranking.setPartidosJugados(
                            estadistica.getPartidosJugados());

                    ranking.setPartidosGanados(
                            estadistica.getPartidosGanados());

                    ranking.setPartidosPerdidos(
                            estadistica.getPartidosPerdidos());

                    ranking.setSetsGanados(
                            estadistica.getSetsGanados());

                    ranking.setSetsPerdidos(
                            estadistica.getSetsPerdidos());

                    ranking.setPorcentajeVictorias(
                            estadistica.getPorcentajeVictorias());

                    ranking.setLogoUrl(
                            estadistica.getLogoUrl());

                    long puntaje = estadistica.getPartidosGanados() * 3;

                    ranking.setPuntaje(puntaje);

                    return ranking;
                })
                .sorted(
                        Comparator
                                .comparingLong(
                                        EquipoRankingResponse::getPuntaje)
                                .reversed()
                                .thenComparing(
                                        Comparator.comparingLong(
                                                EquipoRankingResponse::getSetsGanados)
                                                .reversed())
                                .thenComparing(
                                        Comparator.comparingDouble(
                                                EquipoRankingResponse::getPorcentajeVictorias)
                                                .reversed()))
                .toList();
    }

    public List<PartidoHistorialEquipoResponse> obtenerHistorial(Long equipoId) {

        return partidoRepository
                .findPartidosFinalizadosByEquipoId(equipoId)
                .stream()
                .map(partido -> {

                    boolean esEquipoA = partido.getEquipoAEntidad()
                            .getId()
                            .equals(equipoId);

                    PartidoHistorialEquipoResponse response = new PartidoHistorialEquipoResponse();

                    response.setPartidoId(
                            partido.getId());

                    if (esEquipoA) {

                        response.setEquipo(
                                partido.getEquipoA());

                        response.setRival(
                                partido.getEquipoB());

                        response.setSetsEquipo(
                                partido.getSetsA());

                        response.setSetsRival(
                                partido.getSetsB());

                    } else {

                        response.setEquipo(
                                partido.getEquipoB());

                        response.setRival(
                                partido.getEquipoA());

                        response.setSetsEquipo(
                                partido.getSetsB());

                        response.setSetsRival(
                                partido.getSetsA());
                    }

                    if (partido.getEquipoGanador() == null) {

                        response.setResultado(
                                "SIN RESULTADO");

                    } else if (partido.getEquipoGanador()
                            .getId()
                            .equals(equipoId)) {

                        response.setResultado(
                                "VICTORIA");

                    } else {

                        response.setResultado(
                                "DERROTA");
                    }

                    response.setFecha(
                            partido.getFechaActualizacion());

                    return response;
                })
                .toList();
    }

    public Optional<EquipoEstadisticaResponse> obtenerEstadisticasPorCampeonato(
            Long equipoId,
            Long campeonatoId) {

        Optional<Equipo> equipoOpt = equipoRepository.findById(equipoId);

        if (equipoOpt.isEmpty()) {
            return Optional.empty();
        }

        Equipo equipo = equipoOpt.get();

        List<Partido> partidos = partidoRepository
                .findPartidosFinalizadosByEquipoIdAndCampeonatoId(
                        equipoId,
                        campeonatoId);

        long jugados = partidos.size();

        long ganados = 0;
        long perdidos = 0;

        long setsGanados = 0;
        long setsPerdidos = 0;

        for (Partido partido : partidos) {

            boolean esEquipoA = partido.getEquipoAEntidad()
                    .getId()
                    .equals(equipoId);

            if (esEquipoA) {
                setsGanados += partido.getSetsA();
                setsPerdidos += partido.getSetsB();
            } else {
                setsGanados += partido.getSetsB();
                setsPerdidos += partido.getSetsA();
            }

            if (partido.getEquipoGanador() == null) {
                continue;
            }

            if (partido.getEquipoGanador()
                    .getId()
                    .equals(equipoId)) {

                ganados++;
            } else {
                perdidos++;
            }
        }

        double porcentaje = jugados == 0
                ? 0
                : (ganados * 100.0) / jugados;

        EquipoEstadisticaResponse response = new EquipoEstadisticaResponse();

        response.setEquipoId(equipo.getId());
        response.setNombre(equipo.getNombre());
        response.setLogoUrl(equipo.getLogoUrl());

        response.setPartidosJugados(jugados);
        response.setPartidosGanados(ganados);
        response.setPartidosPerdidos(perdidos);

        response.setSetsGanados(setsGanados);
        response.setSetsPerdidos(setsPerdidos);

        response.setPorcentajeVictorias(
                Math.round(porcentaje * 100.0) / 100.0);

        return Optional.of(response);
    }

    public List<EquipoRankingResponse> obtenerRankingPorCampeonato(
            Long campeonatoId) {

        return equipoRepository.findAll()
                .stream()
                .map(equipo -> obtenerEstadisticasPorCampeonato(
                        equipo.getId(),
                        campeonatoId)
                        .orElseThrow())
                .filter(estadistica -> estadistica.getPartidosJugados() > 0)
                .map(estadistica -> {

                    EquipoRankingResponse ranking = new EquipoRankingResponse();

                    ranking.setEquipoId(
                            estadistica.getEquipoId());

                    ranking.setNombre(
                            estadistica.getNombre());

                    ranking.setLogoUrl(
                            estadistica.getLogoUrl());

                    ranking.setPartidosJugados(
                            estadistica.getPartidosJugados());

                    ranking.setPartidosGanados(
                            estadistica.getPartidosGanados());

                    ranking.setPartidosPerdidos(
                            estadistica.getPartidosPerdidos());

                    ranking.setSetsGanados(
                            estadistica.getSetsGanados());

                    ranking.setSetsPerdidos(
                            estadistica.getSetsPerdidos());

                    ranking.setPorcentajeVictorias(
                            estadistica.getPorcentajeVictorias());

                    ranking.setPuntaje(
                            estadistica.getPartidosGanados() * 3);

                    return ranking;
                })
                .sorted(
                        Comparator
                                .comparingLong(
                                        EquipoRankingResponse::getPuntaje)
                                .reversed()
                                .thenComparing(
                                        Comparator.comparingLong(
                                                EquipoRankingResponse::getSetsGanados)
                                                .reversed()))
                .toList();
    }
}