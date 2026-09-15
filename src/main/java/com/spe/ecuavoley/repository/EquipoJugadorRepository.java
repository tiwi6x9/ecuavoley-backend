package com.spe.ecuavoley.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spe.ecuavoley.model.EquipoJugador;

public interface EquipoJugadorRepository
        extends JpaRepository<EquipoJugador, Long> {

    List<EquipoJugador> findByCampeonatoIdAndEquipoId(
            Long campeonatoId,
            Long equipoId);

    boolean existsByCampeonatoIdAndEquipoIdAndJugadorId(
            Long campeonatoId,
            Long equipoId,
            Long jugadorId);

    // Nómina de un equipo a través de todos los campeonatos, usada
    // por la pantalla de rankings global (fuera de un campeonato
    // específico), donde no hay un campeonatoId para filtrar.
    List<EquipoJugador> findByEquipoId(
            Long equipoId);
}
