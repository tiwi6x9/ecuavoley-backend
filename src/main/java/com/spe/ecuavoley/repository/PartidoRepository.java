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

    @Query("""
            SELECT p
            FROM Partido p
            WHERE p.estado = com.spe.ecuavoley.model.EstadoPartido.FINALIZADO
            AND (
                p.equipoAEntidad.id = :equipoId
                OR p.equipoBEntidad.id = :equipoId
            )
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

    List<Partido> findByEstadoOrderByFechaActualizacionDesc(
            EstadoPartido estado);
}