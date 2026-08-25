package com.spe.ecuavoley.service;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.spe.ecuavoley.dto.JugadorEstadisticaResponse;
import com.spe.ecuavoley.dto.JugadorRankingResponse;
import com.spe.ecuavoley.dto.PartidoHistorialJugadorResponse;
import com.spe.ecuavoley.model.EstadoPartido;
import com.spe.ecuavoley.model.Jugador;
import com.spe.ecuavoley.model.Partido;
import com.spe.ecuavoley.model.PartidoJugador;
import com.spe.ecuavoley.repository.JugadorRepository;
import com.spe.ecuavoley.repository.PartidoJugadorRepository;
import com.spe.ecuavoley.repository.PartidoRepository;

@Service
public class EstadisticaJugadorService {

    private final JugadorRepository jugadorRepository;
    private final PartidoJugadorRepository partidoJugadorRepository;
    private final PartidoRepository partidoRepository;

    public EstadisticaJugadorService(
            JugadorRepository jugadorRepository,
            PartidoJugadorRepository partidoJugadorRepository,
            PartidoRepository partidoRepository) {

        this.jugadorRepository = jugadorRepository;
        this.partidoJugadorRepository = partidoJugadorRepository;
        this.partidoRepository = partidoRepository;
    }

    public Optional<JugadorEstadisticaResponse> obtenerEstadisticas(Long jugadorId) {

        Optional<Jugador> jugadorOpt = jugadorRepository.findById(jugadorId);

        if (jugadorOpt.isEmpty()) {
            return Optional.empty();
        }

        Jugador jugador = jugadorOpt.get();

        List<PartidoJugador> participaciones = partidoJugadorRepository
                .findByJugadorId(jugadorId);

        long jugados = 0;
        long ganados = 0;
        long perdidos = 0;

        for (PartidoJugador participacion : participaciones) {

            Partido partido = participacion.getPartido();

            // Solo contamos partidos terminados.
            if (partido.getEstado() != EstadoPartido.FINALIZADO) {

                continue;
            }

            jugados++;

            if (partido.getEquipoGanador() == null) {
                // Partido finalizado manualmente
                // sin ganador.
                continue;
            }

            Long equipoJugadorId = participacion
                    .getEquipo()
                    .getId();

            Long equipoGanadorId = partido
                    .getEquipoGanador()
                    .getId();

            if (equipoJugadorId.equals(
                    equipoGanadorId)) {

                ganados++;

            } else {

                perdidos++;
            }
        }

        long mvp = partidoRepository
                .countByMvpJugadorId(
                        jugadorId);

        double porcentaje = jugados == 0
                ? 0
                : (ganados * 100.0) / jugados;

        JugadorEstadisticaResponse response = new JugadorEstadisticaResponse();

        response.setJugadorId(jugador.getId());
        response.setNombre(jugador.getNombre());
        response.setApodo(jugador.getApodo());
        response.setFotoUrl(jugador.getFotoUrl());

        response.setPartidosJugados(jugados);
        response.setPartidosGanados(ganados);
        response.setPartidosPerdidos(perdidos);
        response.setMvp(mvp);

        response.setPorcentajeVictorias(
                Math.round(porcentaje * 100.0)
                        / 100.0);

        return Optional.of(response);
    }

    public List<JugadorRankingResponse> obtenerRanking() {

        return jugadorRepository.findAll()
                .stream()
                .map(jugador -> {

                    JugadorEstadisticaResponse estadistica = obtenerEstadisticas(
                            jugador.getId())
                            .orElseThrow();

                    JugadorRankingResponse ranking = new JugadorRankingResponse();

                    ranking.setJugadorId(
                            estadistica.getJugadorId());

                    ranking.setNombre(
                            estadistica.getNombre());

                    ranking.setApodo(
                            estadistica.getApodo());

                    ranking.setPartidosJugados(
                            estadistica.getPartidosJugados());

                    ranking.setPartidosGanados(
                            estadistica.getPartidosGanados());

                    ranking.setMvp(
                            estadistica.getMvp());

                    ranking.setPorcentajeVictorias(
                            estadistica.getPorcentajeVictorias());

                    ranking.setFotoUrl(
                            estadistica.getFotoUrl());

                    long puntaje = estadistica.getPartidosGanados() * 3
                            +
                            estadistica.getMvp() * 2;

                    ranking.setPuntaje(puntaje);

                    return ranking;
                })
                .sorted(
                        Comparator
                                .comparingLong(
                                        JugadorRankingResponse::getPuntaje)
                                .reversed()
                                .thenComparing(
                                        Comparator.comparingLong(
                                                JugadorRankingResponse::getMvp)
                                                .reversed())
                                .thenComparing(
                                        Comparator.comparingDouble(
                                                JugadorRankingResponse::getPorcentajeVictorias)
                                                .reversed()))
                .toList();
    }

    public List<PartidoHistorialJugadorResponse> obtenerHistorial(Long jugadorId) {

        return partidoJugadorRepository
                .findByJugadorId(jugadorId)
                .stream()
                .filter(participacion -> participacion
                        .getPartido()
                        .getEstado() == EstadoPartido.FINALIZADO)
                .map(participacion -> {

                    Partido partido = participacion.getPartido();

                    Long equipoJugadorId = participacion
                            .getEquipo()
                            .getId();

                    boolean esEquipoA = partido
                            .getEquipoAEntidad()
                            .getId()
                            .equals(equipoJugadorId);

                    PartidoHistorialJugadorResponse response = new PartidoHistorialJugadorResponse();

                    response.setPartidoId(
                            partido.getId());

                    if (esEquipoA) {

                        response.setEquipoPropio(
                                partido.getEquipoA());

                        response.setEquipoRival(
                                partido.getEquipoB());

                        response.setSetsPropio(
                                partido.getSetsA());

                        response.setSetsRival(
                                partido.getSetsB());

                    } else {

                        response.setEquipoPropio(
                                partido.getEquipoB());

                        response.setEquipoRival(
                                partido.getEquipoA());

                        response.setSetsPropio(
                                partido.getSetsB());

                        response.setSetsRival(
                                partido.getSetsA());
                    }

                    if (partido.getEquipoGanador() == null) {

                        response.setResultado(
                                "SIN RESULTADO");

                    } else if (partido
                            .getEquipoGanador()
                            .getId()
                            .equals(equipoJugadorId)) {

                        response.setResultado(
                                "VICTORIA");

                    } else {

                        response.setResultado(
                                "DERROTA");
                    }

                    response.setMvp(
                            partido.getMvpJugador() != null &&
                                    partido
                                            .getMvpJugador()
                                            .getId()
                                            .equals(jugadorId));

                    response.setFecha(
                            partido.getFechaActualizacion());

                    return response;
                })
                .toList();
    }

    public Optional<JugadorEstadisticaResponse> obtenerEstadisticasPorCampeonato(
            Long jugadorId,
            Long campeonatoId) {

        Optional<Jugador> jugadorOpt = jugadorRepository.findById(jugadorId);

        if (jugadorOpt.isEmpty()) {
            return Optional.empty();
        }

        Jugador jugador = jugadorOpt.get();

        List<PartidoJugador> participaciones = partidoJugadorRepository
                .findByJugadorIdAndPartidoCampeonatoId(
                        jugadorId,
                        campeonatoId);

        long jugados = 0;
        long ganados = 0;
        long perdidos = 0;

        for (PartidoJugador participacion : participaciones) {

            Partido partido = participacion.getPartido();

            if (partido.getEstado() != EstadoPartido.FINALIZADO) {
                continue;
            }

            jugados++;

            if (partido.getEquipoGanador() == null) {
                continue;
            }

            Long equipoJugadorId = participacion.getEquipo().getId();

            Long equipoGanadorId = partido.getEquipoGanador().getId();

            if (equipoJugadorId.equals(equipoGanadorId)) {
                ganados++;
            } else {
                perdidos++;
            }
        }

        long mvp = partidoRepository
                .countByMvpJugadorIdAndCampeonatoId(
                        jugadorId,
                        campeonatoId);

        double porcentaje = jugados == 0
                ? 0
                : (ganados * 100.0) / jugados;

        JugadorEstadisticaResponse response = new JugadorEstadisticaResponse();

        response.setJugadorId(jugador.getId());
        response.setNombre(jugador.getNombre());
        response.setApodo(jugador.getApodo());
        response.setFotoUrl(jugador.getFotoUrl());

        response.setPartidosJugados(jugados);
        response.setPartidosGanados(ganados);
        response.setPartidosPerdidos(perdidos);
        response.setMvp(mvp);

        response.setPorcentajeVictorias(
                Math.round(porcentaje * 100.0) / 100.0);

        return Optional.of(response);
    }

    public List<JugadorRankingResponse> obtenerRankingPorCampeonato(
            Long campeonatoId) {

        return jugadorRepository.findAll()
                .stream()
                .map(jugador -> obtenerEstadisticasPorCampeonato(
                        jugador.getId(),
                        campeonatoId)
                        .orElseThrow())
                .filter(estadistica -> estadistica.getPartidosJugados() > 0)
                .map(estadistica -> {

                    JugadorRankingResponse ranking = new JugadorRankingResponse();

                    ranking.setJugadorId(
                            estadistica.getJugadorId());

                    ranking.setNombre(
                            estadistica.getNombre());

                    ranking.setApodo(
                            estadistica.getApodo());

                    ranking.setFotoUrl(
                            estadistica.getFotoUrl());

                    ranking.setPartidosJugados(
                            estadistica.getPartidosJugados());

                    ranking.setPartidosGanados(
                            estadistica.getPartidosGanados());

                    ranking.setMvp(
                            estadistica.getMvp());

                    ranking.setPorcentajeVictorias(
                            estadistica.getPorcentajeVictorias());

                    long puntaje = estadistica.getPartidosGanados() * 3
                            +
                            estadistica.getMvp() * 2;

                    ranking.setPuntaje(puntaje);

                    return ranking;
                })
                .sorted(
                        Comparator
                                .comparingLong(
                                        JugadorRankingResponse::getPuntaje)
                                .reversed()
                                .thenComparing(
                                        Comparator.comparingLong(
                                                JugadorRankingResponse::getMvp)
                                                .reversed()))
                .toList();
    }

}