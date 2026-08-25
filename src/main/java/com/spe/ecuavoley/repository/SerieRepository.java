package com.spe.ecuavoley.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spe.ecuavoley.model.Serie;

public interface SerieRepository
        extends JpaRepository<Serie, Long> {

    List<Serie> findByCampeonatoIdOrderByNombreAsc(
            Long campeonatoId);
}