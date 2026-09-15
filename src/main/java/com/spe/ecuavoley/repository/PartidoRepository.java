package com.spe.ecuavoley.repository;

import com.spe.ecuavoley.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.spe.ecuavoley.model.EstadoPartido;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

    List<Partido> findByEstado(EstadoPartido estado);

    List<Partido> findByCampeonatoId(Long campeonatoId);

    List<Partido> findByFechaCampeonatoId(Long fechaCampeonatoId);

    long countByMvpJugadorId(
            Long jugadorId);

    // Ordenado por id descendente (orden de creación, más reciente
    // primero) para que el historial del perfil de equipo coincida
    // con el orden real en que se jugaron los partidos.
    @Query("""
            SELECT p
            FROM Partido p
            WHERE p.estado = com.spe.ecuavoley.model.EstadoPartido.FINALIZADO
            AND (
                p.equipoAEntidad.id = :equipoId
                OR p.equipoBEntidad.id = :equipoId
            )
            ORDER BY p.id DESC
            """)
    List<Partido> findPartidosFinalizadosByEquipoId(
            @Param("equipoId") Long equipoId);

    long countByMvpJugadorIdAndCampeonatoId(
            Long jugadorId,
            Long campeonatoId);

    @Query("""
            SELECT p
            FROM Partido p
            WHERE p.estado = com.spe.ecuavoley.model.EstadoPartido.FINALIZADO
            AND p.campeonato.id = :campeonatoId
            AND (
                p.equipoAEntidad.id = :equipoId
                OR p.equipoBEntidad.id = :equipoId
            )
            ORDER BY p.id DESC
            """)
    List<Partido> findPartidosFinalizadosByEquipoIdAndCampeonatoId(
            @Param("equipoId") Long equipoId,
            @Param("campeonatoId") Long campeonatoId);

    List<Partido> findBySerieIdAndEstado(
            Long serieId,
            EstadoPartido estado);

    List<Partido> findByCampeonatoIdAndEstadoOrderByFechaCampeonatoNumeroAsc(
            Long campeonatoId,
            EstadoPartido estado);

    List<Partido> findByEstadoOrderByFechaCampeonatoFechaAscHoraProgramadaAsc(
            EstadoPartido estado);

    List<Partido> findByCampeonatoIdAndEstadoOrderByFechaCampeonatoFechaAscHoraProgramadaAsc(
            Long campeonatoId,
            EstadoPartido estado);

    // Se ordena por id (orden de creación) en vez de por
    // fecha_actualizacion, porque esa fecha se actualiza en cada punto
    // marcado y no refleja cuándo se creó/jugó el partido: un partido
    // viejo que recibe una corrección después de otros más nuevos se
    // subía al tope del historial, dando un orden que no correspondía
    // a cuándo se jugó realmente.
    List<Partido> findByEstadoOrderByIdDesc(
            EstadoPartido estado);
}
