package com.spe.ecuavoley.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spe.ecuavoley.model.SerieEquipo;

public interface SerieEquipoRepository
        extends JpaRepository<SerieEquipo, Long> {

    List<SerieEquipo> findBySerieId(
            Long serieId);

    boolean existsBySerieIdAndEquipoId(
            Long serieId,
            Long equipoId);
}