package com.spe.ecuavoley.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spe.ecuavoley.model.Jugador;

public interface JugadorRepository
        extends JpaRepository<Jugador, Long> {
}