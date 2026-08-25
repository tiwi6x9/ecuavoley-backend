package com.spe.ecuavoley.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.spe.ecuavoley.model.FechaCampeonato;

public interface FechaCampeonatoRepository
        extends JpaRepository<FechaCampeonato, Long> {

    List<FechaCampeonato> findByCampeonatoIdOrderByNumeroAsc(
            Long campeonatoId);
            
}