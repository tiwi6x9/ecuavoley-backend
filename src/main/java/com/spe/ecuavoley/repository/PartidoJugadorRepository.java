package com.spe.ecuavoley.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spe.ecuavoley.model.PartidoJugador;

public interface PartidoJugadorRepository
                extends JpaRepository<PartidoJugador, Long> {

        List<PartidoJugador> findByPartidoId(
                        Long partidoId);

        boolean existsByPartidoIdAndJugadorId(
                        Long partidoId,
                        Long jugadorId);

        void deleteByPartidoId(
                        Long partidoId);

        long countByJugadorId(
                        Long jugadorId);

        List<PartidoJugador> findByJugadorId(
                        Long jugadorId);

        List<PartidoJugador> findByJugadorIdAndPartidoCampeonatoId(
                        Long jugadorId,
                        Long campeonatoId);

}