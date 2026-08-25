package com.spe.ecuavoley.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spe.ecuavoley.dto.TablaSerieResponse;
import com.spe.ecuavoley.model.Equipo;
import com.spe.ecuavoley.model.EstadoPartido;
import com.spe.ecuavoley.model.Partido;
import com.spe.ecuavoley.model.SerieEquipo;
import com.spe.ecuavoley.repository.PartidoRepository;
import com.spe.ecuavoley.repository.SerieEquipoRepository;

@Service
public class TablaSerieService {

    private final SerieEquipoRepository serieEquipoRepository;
    private final PartidoRepository partidoRepository;

    public TablaSerieService(
            SerieEquipoRepository serieEquipoRepository,
            PartidoRepository partidoRepository) {

        this.serieEquipoRepository = serieEquipoRepository;

        this.partidoRepository = partidoRepository;
    }

    public List<TablaSerieResponse> obtenerTabla(
            Long serieId) {

        List<SerieEquipo> equiposSerie = serieEquipoRepository
                .findBySerieId(serieId);

        List<Partido> partidos = partidoRepository
                .findBySerieIdAndEstado(
                        serieId,
                        EstadoPartido.FINALIZADO);

        List<TablaSerieResponse> tabla = new ArrayList<>();

        for (SerieEquipo relacion : equiposSerie) {

            Equipo equipo = relacion.getEquipo();

            TablaSerieResponse fila = new TablaSerieResponse();

            fila.setEquipoId(equipo.getId());
            fila.setEquipoNombre(equipo.getNombre());
            fila.setLogoUrl(equipo.getLogoUrl());

            long pj = 0;
            long pg = 0;
            long pp = 0;

            long sg = 0;
            long sp = 0;

            long puntos = 0;

            for (Partido partido : partidos) {

                boolean esA = partido.getEquipoAEntidad()
                        .getId()
                        .equals(equipo.getId());

                boolean esB = partido.getEquipoBEntidad()
                        .getId()
                        .equals(equipo.getId());

                if (!esA && !esB) {
                    continue;
                }

                pj++;

                int setsEquipo;
                int setsRival;

                if (esA) {
                    setsEquipo = partido.getSetsA();
                    setsRival = partido.getSetsB();
                } else {
                    setsEquipo = partido.getSetsB();
                    setsRival = partido.getSetsA();
                }

                sg += setsEquipo;
                sp += setsRival;

                // Finalización manual sin ganador:
                // no entrega puntos.
                if (partido.getEquipoGanador() == null) {
                    continue;
                }

                boolean gano = partido
                        .getEquipoGanador()
                        .getId()
                        .equals(equipo.getId());

                if (gano) {

                    pg++;

                    if (setsRival == 0) {
                        puntos += 3;
                    } else {
                        puntos += 2;
                    }

                } else {

                    pp++;

                    // Perdedor recibe 1 punto
                    // solamente si el partido terminó 2-1.
                    if (setsEquipo == 1 &&
                            setsRival == 2) {

                        puntos += 1;
                    }
                }
            }

            fila.setPartidosJugados(pj);
            fila.setPartidosGanados(pg);
            fila.setPartidosPerdidos(pp);

            fila.setSetsGanados(sg);
            fila.setSetsPerdidos(sp);

            fila.setDiferenciaSets(
                    sg - sp);

            fila.setPuntos(puntos);

            tabla.add(fila);
        }

        return tabla
                .stream()
                .sorted(
                        Comparator
                                .comparingLong(
                                        TablaSerieResponse::getPuntos)
                                .reversed()
                                .thenComparing(
                                        Comparator.comparingLong(
                                                TablaSerieResponse::getSetsGanados)
                                                .reversed())
                                .thenComparing(
                                        Comparator.comparingLong(
                                                TablaSerieResponse::getDiferenciaSets)
                                                .reversed())
                                .thenComparing(
                                        Comparator.comparingLong(
                                                TablaSerieResponse::getPartidosGanados)
                                                .reversed()))
                .toList();
    }
}