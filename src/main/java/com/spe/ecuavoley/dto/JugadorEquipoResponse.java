package com.spe.ecuavoley.dto;

public class JugadorEquipoResponse {

    private Long id;
    private String nombre;
    private String fotoUrl;

    public JugadorEquipoResponse() {
    }

    public JugadorEquipoResponse(
            Long id,
            String nombre,
            String fotoUrl) {

        this.id = id;
        this.nombre = nombre;
        this.fotoUrl = fotoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}