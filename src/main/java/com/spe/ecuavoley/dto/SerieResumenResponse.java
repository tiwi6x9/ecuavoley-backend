package com.spe.ecuavoley.dto;

public class SerieResumenResponse {

    private Long id;
    private String nombre;

    public SerieResumenResponse() {
    }

    public SerieResumenResponse(
            Long id,
            String nombre) {

        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}