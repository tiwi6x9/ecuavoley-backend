package com.spe.ecuavoley.service;

import com.spe.ecuavoley.dto.EquipoEstadisticaResponse;
import com.spe.ecuavoley.model.Equipo;
import com.spe.ecuavoley.model.Partido;
import com.spe.ecuavoley.repository.EquipoRepository;
import com.spe.ecuavoley.repository.PartidoRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Pruebas del calculo de estadisticas de equipo.
 *
 * Este calculo se hace a mano recorriendo partidos en memoria (no
 * con una consulta SQL agregada), asi que es un punto donde un
 * cambio futuro podria introducir un numero incorrecto sin que nada
 * lo detecte en producción. Estas pruebas fijan el comportamiento
 * correcto actual.
 */
class EstadisticaEquipoServiceTest {

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private PartidoRepository partidoRepository;

    private AutoCloseable mocks;
    private EstadisticaEquipoService service;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        service = new EstadisticaEquipoService(equipoRepository, partidoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void calculaVictoriasDerrotasYSetsCorrectamente() {
        Equipo equipoLocal = crearEquipo(1L, "Tigres");
        Equipo equipoRival = crearEquipo(2L, "Halcones");

        // El equipo consultado juega como A y gana 3-1.
        Partido partidoComoLocal = new Partido();
        partidoComoLocal.setEquipoAEntidad(equipoLocal);
        partidoComoLocal.setEquipoBEntidad(equipoRival);
        partidoComoLocal.setSetsA(3);
        partidoComoLocal.setSetsB(1);
        partidoComoLocal.setEquipoGanador(equipoLocal);

        // El equipo consultado juega como B y pierde 1-3.
        Partido partidoComoVisitante = new Partido();
        partidoComoVisitante.setEquipoAEntidad(equipoRival);
        partidoComoVisitante.setEquipoBEntidad(equipoLocal);
        partidoComoVisitante.setSetsA(3);
        partidoComoVisitante.setSetsB(1);
        partidoComoVisitante.setEquipoGanador(equipoRival);

        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipoLocal));
        when(partidoRepository.findPartidosFinalizadosByEquipoId(1L))
                .thenReturn(List.of(partidoComoLocal, partidoComoVisitante));

        Optional<EquipoEstadisticaResponse> resultado = service.obtenerEstadisticas(1L);

        assertTrue(resultado.isPresent());
        EquipoEstadisticaResponse estadisticas = resultado.get();

        assertEquals(2, estadisticas.getPartidosJugados());
        assertEquals(1, estadisticas.getPartidosGanados());
        assertEquals(1, estadisticas.getPartidosPerdidos());

        // Sets ganados: 3 (jugando de local) + 1 (jugando de visitante) = 4
        assertEquals(4, estadisticas.getSetsGanados());

        // Sets perdidos: 1 (jugando de local) + 3 (jugando de visitante) = 4
        assertEquals(4, estadisticas.getSetsPerdidos());

        assertEquals(50.0, estadisticas.getPorcentajeVictorias(), 0.001);
    }

    @Test
    void equipoSinPartidosDevuelvePorcentajeCero() {
        Equipo equipo = crearEquipo(3L, "Equipo Nuevo");

        when(equipoRepository.findById(3L)).thenReturn(Optional.of(equipo));
        when(partidoRepository.findPartidosFinalizadosByEquipoId(3L))
                .thenReturn(List.of());

        Optional<EquipoEstadisticaResponse> resultado = service.obtenerEstadisticas(3L);

        assertTrue(resultado.isPresent());
        assertEquals(0, resultado.get().getPartidosJugados());
        assertEquals(0.0, resultado.get().getPorcentajeVictorias(), 0.001);
    }

    @Test
    void equipoInexistenteDevuelveVacio() {
        when(equipoRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<EquipoEstadisticaResponse> resultado = service.obtenerEstadisticas(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void partidoSinGanadorNoSumaVictoriaNiDerrota() {
        Equipo equipoLocal = crearEquipo(1L, "Tigres");
        Equipo equipoRival = crearEquipo(2L, "Halcones");

        Partido partidoSinGanador = new Partido();
        partidoSinGanador.setEquipoAEntidad(equipoLocal);
        partidoSinGanador.setEquipoBEntidad(equipoRival);
        partidoSinGanador.setSetsA(1);
        partidoSinGanador.setSetsB(1);
        partidoSinGanador.setEquipoGanador(null);

        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipoLocal));
        when(partidoRepository.findPartidosFinalizadosByEquipoId(1L))
                .thenReturn(List.of(partidoSinGanador));

        Optional<EquipoEstadisticaResponse> resultado = service.obtenerEstadisticas(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getPartidosJugados());
        assertEquals(0, resultado.get().getPartidosGanados());
        assertEquals(0, resultado.get().getPartidosPerdidos());
    }

    // Equipo no tiene un setId publico (el id lo genera JPA), asi
    // que se usa reflexion para armar datos de prueba. No modifica
    // nada del codigo de produccion.
    private Equipo crearEquipo(Long id, String nombre) {
        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);

        try {
            Field idField = Equipo.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(equipo, id);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(
                    "No se pudo preparar el Equipo de prueba", e);
        }

        return equipo;
    }
}
