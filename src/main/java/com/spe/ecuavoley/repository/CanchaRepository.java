package com.spe.ecuavoley.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spe.ecuavoley.model.Cancha;


public interface CanchaRepository
        extends JpaRepository<Cancha, Long> {
}