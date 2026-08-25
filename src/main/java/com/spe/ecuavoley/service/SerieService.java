package com.spe.ecuavoley.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spe.ecuavoley.dto.SerieResumenResponse;
import com.spe.ecuavoley.repository.SerieRepository;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    public SerieService(
            SerieRepository serieRepository) {

        this.serieRepository = serieRepository;
    }

    public List<SerieResumenResponse> obtenerPorCampeonato(
            Long campeonatoId) {

        return serieRepository
                .findByCampeonatoIdOrderByNombreAsc(
                        campeonatoId)
                .stream()
                .map(serie -> new SerieResumenResponse(
                        serie.getId(),
                        serie.getNombre()))
                .toList();
    }
}