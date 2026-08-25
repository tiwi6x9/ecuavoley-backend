package com.spe.ecuavoley.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.spe.ecuavoley.model.Equipo;


public interface EquipoRepository
        extends JpaRepository<Equipo, Long> {
}