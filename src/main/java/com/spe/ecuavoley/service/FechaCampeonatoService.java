package com.spe.ecuavoley.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spe.ecuavoley.model.FechaCampeonato;
import com.spe.ecuavoley.repository.FechaCampeonatoRepository;

@Service
public class FechaCampeonatoService {

    private final FechaCampeonatoRepository fechaCampeonatoRepository;

    public FechaCampeonatoService(
            FechaCampeonatoRepository fechaCampeonatoRepository) {

        this.fechaCampeonatoRepository =
                fechaCampeonatoRepository;
    }

    public List<FechaCampeonato> obtenerPorCampeonato(
            Long campeonatoId) {

        return fechaCampeonatoRepository
                .findByCampeonatoIdOrderByNumeroAsc(
                        campeonatoId);
    }
}