package com.spe.ecuavoley.service;

import com.spe.ecuavoley.dto.ActualizarEstadoPartidoRequest;
import com.spe.ecuavoley.dto.CrearPartidoRequest;
import com.spe.ecuavoley.mapper.PartidoMapper;
import com.spe.ecuavoley.model.Equipo;
import com.spe.ecuavoley.model.EstadoPartido;
import com.spe.ecuavoley.model.Partido;
import com.spe.ecuavoley.repository.CampeonatoRepository;
import com.spe.ecuavoley.repository.CanchaRepository;
import com.spe.ecuavoley.repository.EquipoRepository;
import com.spe.ecuavoley.repository.FechaCampeonatoRepository;
import com.spe.ecuavoley.repository.PartidoRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas de la logica de partidos: es literalmente el marcador que
 * se usa en vivo durante un campeonato, asi que cualquier cambio
 * futuro en este servicio debe seguir pasando estas pruebas antes de
 * llegar a un partido real.
 */
class PartidoServiceTest {

    @Mock
    private PartidoRepository partidoRepository;

    @Mock
    private PartidoSseService partidoSseService;

    @Mock
    private CampeonatoRepository campeonatoRepository;

    @Mock
    private CanchaRepository canchaRepository;

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private FechaCampeonatoRepository fechaCampeonatoRepository;

    @Mock
    private PartidoMapper partidoMapper;

    private AutoCloseable mocks;
    private PartidoService partidoService;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        partidoService = new PartidoService(
                partidoRepository,
                partidoSseService,
                campeonatoRepository,
                canchaRepository,
                equipoRepository,
                fechaCampeonatoRepository,
                partidoMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void crearPartidoFijaValoresInicialesDesdeElServidor() {
        CrearPartidoRequest request = new CrearPartidoRequest();
        request.setEquipoA("Tigres");
        request.setEquipoB("Halcones");
        request.setMetaPuntos(25);

        when(partidoRepository.save(any(Partido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Partido resultado = partidoService.crearPartido(request);

        assertEquals("Tigres", resultado.getEquipoA());
        assertEquals("Halcones", resultado.getEquipoB());
        assertEquals(25, resultado.getMetaPuntos());
        assertEquals(0, resultado.getPuntosA());
        assertEquals(0, resultado.getPuntosB());
        assertEquals(0, resultado.getSetsA());
        assertEquals(0, resultado.getSetsB());
        assertEquals(1, resultado.getSetActual());
        assertEquals("A", resultado.getEquipoCambio());
        assertEquals(EstadoPartido.EN_JUEGO, resultado.getEstado());
    }

    @Test
    void actualizarEstadoAFinalizadoAsignaGanadorPorSets() {
        Equipo equipoA = new Equipo();
        Equipo equipoB = new Equipo();

        Partido partido = new Partido();
        partido.setEquipoAEntidad(equipoA);
        partido.setEquipoBEntidad(equipoB);

        when(partidoRepository.findById(10L)).thenReturn(Optional.of(partido));
        when(partidoRepository.save(any(Partido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ActualizarEstadoPartidoRequest request = new ActualizarEstadoPartidoRequest();
        request.setPuntosA(25);
        request.setPuntosB(20);
        request.setSetsA(2);
        request.setSetsB(1);
        request.setSetActual(3);
        request.setMetaPuntos(25);
        request.setEquipoCambio("A");
        request.setEstado(EstadoPartido.FINALIZADO);

        Optional<Partido> actualizado = partidoService.actualizarEstado(10L, request);

        assertTrue(actualizado.isPresent());
        assertEquals(equipoA, actualizado.get().getEquipoGanador());
        verify(partidoSseService).enviarActualizacion(actualizado.get());
    }

    @Test
    void actualizarEstadoConEmpateDeSetsNoAsignaGanador() {
        Partido partido = new Partido();
        partido.setEquipoAEntidad(new Equipo());
        partido.setEquipoBEntidad(new Equipo());

        when(partidoRepository.findById(10L)).thenReturn(Optional.of(partido));
        when(partidoRepository.save(any(Partido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ActualizarEstadoPartidoRequest request = new ActualizarEstadoPartidoRequest();
        request.setSetsA(1);
        request.setSetsB(1);
        request.setEquipoCambio("A");
        request.setEstado(EstadoPartido.FINALIZADO);

        Optional<Partido> actualizado = partidoService.actualizarEstado(10L, request);

        assertTrue(actualizado.isPresent());
        assertNull(actualizado.get().getEquipoGanador());
    }

    @Test
    void actualizarEstadoDePartidoInexistenteDevuelveVacio() {
        when(partidoRepository.findById(99L)).thenReturn(Optional.empty());

        ActualizarEstadoPartidoRequest request = new ActualizarEstadoPartidoRequest();
        request.setEstado(EstadoPartido.EN_JUEGO);
        request.setEquipoCambio("A");

        Optional<Partido> actualizado = partidoService.actualizarEstado(99L, request);

        assertTrue(actualizado.isEmpty());
        verify(partidoRepository, never()).save(any());
    }

    @Test
    void iniciarPartidoProgramadoPasaAEnJuego() {
        Partido partido = new Partido();
        partido.setEstado(EstadoPartido.PROGRAMADO);

        when(partidoRepository.findById(5L)).thenReturn(Optional.of(partido));
        when(partidoRepository.save(any(Partido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Partido> resultado = partidoService.iniciarPartido(5L);

        assertTrue(resultado.isPresent());
        assertEquals(EstadoPartido.EN_JUEGO, resultado.get().getEstado());
        verify(partidoSseService).enviarActualizacion(resultado.get());
    }

    @Test
    void iniciarPartidoQueNoEstaProgramadoNoCambiaNada() {
        Partido partido = new Partido();
        partido.setEstado(EstadoPartido.EN_JUEGO);

        when(partidoRepository.findById(5L)).thenReturn(Optional.of(partido));

        Optional<Partido> resultado = partidoService.iniciarPartido(5L);

        assertTrue(resultado.isPresent());
        assertEquals(EstadoPartido.EN_JUEGO, resultado.get().getEstado());
        verify(partidoRepository, never()).save(any());
        verify(partidoSseService, never()).enviarActualizacion(any());
    }
}
