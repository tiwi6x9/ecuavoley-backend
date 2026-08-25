package com.spe.ecuavoley.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spe.ecuavoley.dto.JugadorResumenResponse;
import com.spe.ecuavoley.dto.SeleccionarJugadoresPartidoRequest;
import com.spe.ecuavoley.model.Equipo;
import com.spe.ecuavoley.model.EstadoPartido;
import com.spe.ecuavoley.model.Jugador;
import com.spe.ecuavoley.model.Partido;
import com.spe.ecuavoley.model.PartidoJugador;
import com.spe.ecuavoley.repository.EquipoJugadorRepository;
import com.spe.ecuavoley.repository.JugadorRepository;
import com.spe.ecuavoley.repository.PartidoJugadorRepository;
import com.spe.ecuavoley.repository.PartidoRepository;


@Service
public class PartidoJugadorService {

        private final PartidoRepository partidoRepository;
        private final JugadorRepository jugadorRepository;
        private final EquipoJugadorRepository equipoJugadorRepository;
        private final PartidoJugadorRepository partidoJugadorRepository;

        public PartidoJugadorService(
                        PartidoRepository partidoRepository,
                        JugadorRepository jugadorRepository,
                        EquipoJugadorRepository equipoJugadorRepository,
                        PartidoJugadorRepository partidoJugadorRepository) {

                this.partidoRepository = partidoRepository;
                this.jugadorRepository = jugadorRepository;
                this.equipoJugadorRepository = equipoJugadorRepository;
                this.partidoJugadorRepository = partidoJugadorRepository;
        }

        @Transactional
        public boolean seleccionarJugadores(
                        Long partidoId,
                        SeleccionarJugadoresPartidoRequest request) {

                Optional<Partido> partidoOpt = partidoRepository.findById(partidoId);

                if (partidoOpt.isEmpty()) {
                        return false;
                }

                Partido partido = partidoOpt.get();

                if (partido.getCampeonato() == null ||
                                partido.getEquipoAEntidad() == null ||
                                partido.getEquipoBEntidad() == null) {

                        return false;
                }

                Long campeonatoId = partido.getCampeonato().getId();

                List<Long> jugadoresA = request.getJugadoresEquipoA();

                List<Long> jugadoresB = request.getJugadoresEquipoB();

                if (jugadoresA == null ||
                                jugadoresB == null ||
                                jugadoresA.isEmpty() ||
                                jugadoresB.isEmpty()) {

                        return false;
                }

                if (!validarJugadores(
                                campeonatoId,
                                partido.getEquipoAEntidad().getId(),
                                jugadoresA)) {

                        return false;
                }

                if (!validarJugadores(
                                campeonatoId,
                                partido.getEquipoBEntidad().getId(),
                                jugadoresB)) {

                        return false;
                }

                partidoJugadorRepository
                                .deleteByPartidoId(partidoId);

                guardarJugadores(
                                partido,
                                partido.getEquipoAEntidad(),
                                jugadoresA);

                guardarJugadores(
                                partido,
                                partido.getEquipoBEntidad(),
                                jugadoresB);

                return true;
        }

        private boolean validarJugadores(
                        Long campeonatoId,
                        Long equipoId,
                        List<Long> jugadores) {

                for (Long jugadorId : jugadores) {

                        if (!jugadorRepository.existsById(
                                        jugadorId)) {

                                return false;
                        }

                        if (!equipoJugadorRepository
                                        .existsByCampeonatoIdAndEquipoIdAndJugadorId(
                                                        campeonatoId,
                                                        equipoId,
                                                        jugadorId)) {

                                return false;
                        }
                }

                return true;
        }

        private void guardarJugadores(
                        Partido partido,
                        Equipo equipo,
                        List<Long> jugadores) {

                for (Long jugadorId : jugadores) {

                        Jugador jugador = jugadorRepository
                                        .findById(jugadorId)
                                        .orElseThrow();

                        PartidoJugador relacion = new PartidoJugador();

                        relacion.setPartido(partido);
                        relacion.setEquipo(equipo);
                        relacion.setJugador(jugador);

                        partidoJugadorRepository.save(
                                        relacion);
                }
        }

        public List<JugadorResumenResponse> obtenerJugadoresPartido(
                        Long partidoId) {

                List<JugadorResumenResponse> resultado = new ArrayList<>();

                partidoJugadorRepository
                                .findByPartidoId(partidoId)
                                .forEach(relacion -> {

                                        Jugador jugador = relacion.getJugador();

                                        resultado.add(
                                                        new JugadorResumenResponse(
                                                                        jugador.getId(),
                                                                        jugador.getNombre(),
                                                                        jugador.getApodo(),
                                                                        jugador.getFotoUrl()));
                                });

                return resultado;
        }

        @Transactional
        public boolean seleccionarMvp(
                        Long partidoId,
                        Long jugadorId) {

                Optional<Partido> partidoOpt = partidoRepository.findById(partidoId);

                if (partidoOpt.isEmpty()) {
                        return false;
                }

                Partido partido = partidoOpt.get();

                if (partido.getEstado() != EstadoPartido.FINALIZADO) {
                        return false;
                }

                if (!partidoJugadorRepository
                                .existsByPartidoIdAndJugadorId(
                                                partidoId,
                                                jugadorId)) {

                        return false;
                }

                Optional<Jugador> jugadorOpt = jugadorRepository.findById(jugadorId);

                if (jugadorOpt.isEmpty()) {
                        return false;
                }

                partido.setMvpJugador(
                                jugadorOpt.get());

                partidoRepository.save(partido);

                return true;
        }
}