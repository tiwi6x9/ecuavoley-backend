package com.spe.ecuavoley.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spe.ecuavoley.dto.ActualizarEstadoPartidoRequest;
import com.spe.ecuavoley.model.EstadoPartido;
import com.spe.ecuavoley.model.FechaCampeonato;
import com.spe.ecuavoley.model.Partido;
import com.spe.ecuavoley.repository.CampeonatoRepository;
import com.spe.ecuavoley.repository.EquipoRepository;
import com.spe.ecuavoley.repository.PartidoRepository;

import com.spe.ecuavoley.dto.CrearPartidoProgramadoRequest;
import com.spe.ecuavoley.dto.HistorialPartidoResponse;
import com.spe.ecuavoley.model.Campeonato;
import com.spe.ecuavoley.model.Cancha;
import com.spe.ecuavoley.model.Equipo;
import com.spe.ecuavoley.repository.CanchaRepository;
import com.spe.ecuavoley.repository.FechaCampeonatoRepository;

import com.spe.ecuavoley.dto.PartidoEnVivoResponse;
import com.spe.ecuavoley.dto.ProximoPartidoResponse;
import com.spe.ecuavoley.dto.ResultadoPartidoResponse;
import com.spe.ecuavoley.mapper.PartidoMapper;


import java.util.List;

@Service
public class PartidoService {

        private final PartidoRepository partidoRepository;
        private final PartidoSseService partidoSseService;
        private final CampeonatoRepository campeonatoRepository;
        private final CanchaRepository canchaRepository;
        private final EquipoRepository equipoRepository;
        private final FechaCampeonatoRepository fechaCampeonatoRepository;
        private final PartidoMapper partidoMapper;

        public PartidoService(
                        PartidoRepository partidoRepository,
                        PartidoSseService partidoSseService,
                        CampeonatoRepository campeonatoRepository,
                        CanchaRepository canchaRepository,
                        EquipoRepository equipoRepository,
                        FechaCampeonatoRepository fechaCampeonatoRepository,
                        PartidoMapper partidoMapper) {

                this.partidoRepository = partidoRepository;
                this.partidoSseService = partidoSseService;
                this.campeonatoRepository = campeonatoRepository;
                this.canchaRepository = canchaRepository;
                this.equipoRepository = equipoRepository;
                this.fechaCampeonatoRepository = fechaCampeonatoRepository;
                this.partidoMapper = partidoMapper;
        }

        public List<PartidoEnVivoResponse> obtenerPartidosEnVivo() {

                return partidoRepository
                                .findByEstado(EstadoPartido.EN_JUEGO)
                                .stream()
                                .map(partidoMapper::toEnVivoResponse)
                                .toList();
        }

        public Partido crearPartido(Partido partido) {
                return partidoRepository.save(partido);
        }

        public Optional<Partido> obtenerPorId(Long id) {
                return partidoRepository.findById(id);
        }

        public Optional<Partido> actualizarEstado(
                        Long id,
                        ActualizarEstadoPartidoRequest request) {

                return partidoRepository.findById(id)
                                .map(partido -> {

                                        partido.setPuntosA(request.getPuntosA());
                                        partido.setPuntosB(request.getPuntosB());
                                        partido.setSetsA(request.getSetsA());
                                        partido.setSetsB(request.getSetsB());
                                        partido.setSetActual(request.getSetActual());
                                        partido.setMetaPuntos(request.getMetaPuntos());
                                        partido.setEquipoCambio(request.getEquipoCambio());
                                        partido.setEstado(request.getEstado());

                                        if (request.getEstado() == EstadoPartido.FINALIZADO) {

                                                if (partido.getSetsA() > partido.getSetsB()) {
                                                        partido.setEquipoGanador(
                                                                        partido.getEquipoAEntidad());

                                                } else if (partido.getSetsB() > partido.getSetsA()) {
                                                        partido.setEquipoGanador(
                                                                        partido.getEquipoBEntidad());

                                                } else {
                                                        // Finalización manual sin ganador claro.
                                                        partido.setEquipoGanador(null);
                                                }
                                        }

                                        Partido partidoActualizado = partidoRepository.save(partido);

                                        partidoSseService.enviarActualizacion(
                                                        partidoActualizado);

                                        return partidoActualizado;
                                });
        }

        public Optional<Partido> crearPartidoProgramado(
                        CrearPartidoProgramadoRequest request) {

                Optional<Campeonato> campeonatoOpt = campeonatoRepository.findById(request.getCampeonatoId());

                Optional<Cancha> canchaOpt = canchaRepository.findById(request.getCanchaId());

                Optional<Equipo> equipoAOpt = equipoRepository.findById(request.getEquipoAId());

                Optional<Equipo> equipoBOpt = equipoRepository.findById(request.getEquipoBId());

                Optional<FechaCampeonato> fechaOpt = fechaCampeonatoRepository.findById(
                                request.getFechaCampeonatoId());

                if (campeonatoOpt.isEmpty() ||
                                canchaOpt.isEmpty() ||
                                equipoAOpt.isEmpty() ||
                                equipoBOpt.isEmpty() ||
                                fechaOpt.isEmpty()) {

                        return Optional.empty();
                }

                Campeonato campeonato = campeonatoOpt.get();
                Cancha cancha = canchaOpt.get();
                Equipo equipoA = equipoAOpt.get();
                Equipo equipoB = equipoBOpt.get();

                Partido partido = new Partido();

                FechaCampeonato fechaCampeonato = fechaOpt.get();

                if (!fechaCampeonato
                                .getCampeonato()
                                .getId()
                                .equals(campeonato.getId())) {

                        return Optional.empty();
                }

                partido.setFechaCampeonato(
                                fechaCampeonato);

                partido.setCampeonato(campeonato);
                partido.setCancha(cancha);

                partido.setEquipoAEntidad(equipoA);
                partido.setEquipoBEntidad(equipoB);

                // Compatibilidad temporal con Flutter actual.
                partido.setEquipoA(equipoA.getNombre());
                partido.setEquipoB(equipoB.getNombre());

                partido.setMetaPuntos(request.getMetaPuntos());

                partido.setHoraProgramada(
                                request.getHoraProgramada());

                partido.setPuntosA(0);
                partido.setPuntosB(0);

                partido.setSetsA(0);
                partido.setSetsB(0);

                partido.setSetActual(1);
                partido.setEquipoCambio("A");

                // Importante: todavía no está jugándose.
                partido.setEstado(EstadoPartido.PROGRAMADO);

                return Optional.of(
                                partidoRepository.save(partido));
        }

        public List<Partido> obtenerPorCampeonato(
                        Long campeonatoId) {

                return partidoRepository
                                .findByCampeonatoId(campeonatoId);
        }

        public List<Partido> obtenerPorFecha(
                        Long fechaCampeonatoId) {

                return partidoRepository
                                .findByFechaCampeonatoId(
                                                fechaCampeonatoId);
        }

        public Optional<Partido> iniciarPartido(Long id) {

                return partidoRepository.findById(id)
                                .map(partido -> {

                                        if (partido.getEstado() != EstadoPartido.PROGRAMADO) {
                                                return partido;
                                        }

                                        partido.setEstado(
                                                        EstadoPartido.EN_JUEGO);

                                        Partido partidoActualizado = partidoRepository.save(partido);

                                        partidoSseService.enviarActualizacion(
                                                        partidoActualizado);

                                        return partidoActualizado;
                                });
        }

        public Optional<PartidoEnVivoResponse> obtenerDetallePublico(
                        Long id) {

                return partidoRepository
                                .findById(id)
                                .map(partidoMapper::toEnVivoResponse);
        }

        public List<ResultadoPartidoResponse> obtenerResultadosCampeonato(
                        Long campeonatoId) {

                return partidoRepository
                                .findByCampeonatoIdAndEstadoOrderByFechaCampeonatoNumeroAsc(
                                                campeonatoId,
                                                EstadoPartido.FINALIZADO)
                                .stream()
                                .map(this::convertirAResultado)
                                .toList();
        }

        private ResultadoPartidoResponse convertirAResultado(
                        Partido partido) {

                ResultadoPartidoResponse response = new ResultadoPartidoResponse();

                response.setId(partido.getId());

                response.setEquipoA(partido.getEquipoA());
                response.setEquipoB(partido.getEquipoB());

                response.setSetsA(partido.getSetsA());
                response.setSetsB(partido.getSetsB());

                if (partido.getEquipoAEntidad() != null) {
                        response.setEquipoAId(
                                        partido.getEquipoAEntidad().getId());
                }

                if (partido.getEquipoBEntidad() != null) {
                        response.setEquipoBId(
                                        partido.getEquipoBEntidad().getId());
                }

                if (partido.getFechaCampeonato() != null) {
                        response.setFechaNumero(
                                        partido.getFechaCampeonato().getNumero());

                        response.setFecha(
                                        partido.getFechaCampeonato().getFecha());
                }

                if (partido.getCancha() != null) {
                        response.setCanchaNombre(
                                        partido.getCancha().getNombre());
                }

                return response;
        }

        public List<ProximoPartidoResponse> obtenerProximosPartidos() {

                return partidoRepository
                                .findByEstadoOrderByFechaCampeonatoFechaAscHoraProgramadaAsc(
                                                EstadoPartido.PROGRAMADO)
                                .stream()
                                .map(this::convertirAProximoPartido)
                                .toList();
        }

        public List<ProximoPartidoResponse> obtenerProximosPartidosPorCampeonato(
                        Long campeonatoId) {

                return partidoRepository
                                .findByCampeonatoIdAndEstadoOrderByFechaCampeonatoFechaAscHoraProgramadaAsc(
                                                campeonatoId,
                                                EstadoPartido.PROGRAMADO)
                                .stream()
                                .map(this::convertirAProximoPartido)
                                .toList();
        }

        private ProximoPartidoResponse convertirAProximoPartido(
                        Partido partido) {

                ProximoPartidoResponse response = new ProximoPartidoResponse();

                response.setId(partido.getId());

                response.setEquipoA(
                                partido.getEquipoA());

                response.setEquipoB(
                                partido.getEquipoB());

                response.setHoraProgramada(
                                partido.getHoraProgramada());

                if (partido.getCampeonato() != null) {
                        response.setCampeonatoId(
                                        partido.getCampeonato().getId());

                        response.setCampeonatoNombre(
                                        partido.getCampeonato().getNombre());
                }

                if (partido.getFechaCampeonato() != null) {
                        response.setFechaNumero(
                                        partido
                                                        .getFechaCampeonato()
                                                        .getNumero());

                        response.setFecha(
                                        partido
                                                        .getFechaCampeonato()
                                                        .getFecha());
                }

                if (partido.getEquipoAEntidad() != null) {
                        response.setEquipoAId(
                                        partido
                                                        .getEquipoAEntidad()
                                                        .getId());
                }

                if (partido.getEquipoBEntidad() != null) {
                        response.setEquipoBId(
                                        partido
                                                        .getEquipoBEntidad()
                                                        .getId());
                }

                if (partido.getCancha() != null) {
                        response.setCanchaNombre(
                                        partido
                                                        .getCancha()
                                                        .getNombre());
                }

                return response;
        }

        public List<HistorialPartidoResponse> obtenerHistorial() {

                return partidoRepository
                                .findByEstadoOrderByFechaActualizacionDesc(
                                                EstadoPartido.FINALIZADO)
                                .stream()
                                .map(this::convertirAHistorial)
                                .toList();
        }

        private HistorialPartidoResponse convertirAHistorial(
                        Partido partido) {

                HistorialPartidoResponse response = new HistorialPartidoResponse();

                response.setId(partido.getId());

                response.setEquipoA(partido.getEquipoA());
                response.setEquipoB(partido.getEquipoB());

                response.setSetsA(partido.getSetsA());
                response.setSetsB(partido.getSetsB());

                if (partido.getCampeonato() != null) {

                        response.setTipo("CAMPEONATO");

                        response.setCampeonatoId(
                                        partido.getCampeonato().getId());

                        response.setCampeonatoNombre(
                                        partido.getCampeonato().getNombre());

                        if (partido.getFechaCampeonato() != null) {

                                response.setFechaNumero(
                                                partido
                                                                .getFechaCampeonato()
                                                                .getNumero());

                                response.setFecha(
                                                partido
                                                                .getFechaCampeonato()
                                                                .getFecha());
                        }

                        if (partido.getCancha() != null) {
                                response.setCanchaNombre(
                                                partido.getCancha().getNombre());
                        }

                } else {

                        response.setTipo("GENERAL");
                }

                return response;
        }

}