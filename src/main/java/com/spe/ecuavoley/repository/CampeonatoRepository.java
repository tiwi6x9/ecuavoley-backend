package com.spe.ecuavoley.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spe.ecuavoley.model.Campeonato;
import com.spe.ecuavoley.model.EstadoCampeonato;


public interface CampeonatoRepository
        extends JpaRepository<Campeonato, Long> {

    List<Campeonato> findByEstado(
            EstadoCampeonato estado);
}