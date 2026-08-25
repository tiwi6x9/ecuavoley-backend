package com.spe.ecuavoley.service;

import com.spe.ecuavoley.model.Campeonato;
import com.spe.ecuavoley.repository.CampeonatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;

    public CampeonatoService(
            CampeonatoRepository campeonatoRepository) {
        this.campeonatoRepository = campeonatoRepository;
    }

    public List<Campeonato> obtenerTodos() {
        return campeonatoRepository.findAll();
    }

    public Optional<Campeonato> obtenerPorId(Long id) {
        return campeonatoRepository.findById(id);
    }
}