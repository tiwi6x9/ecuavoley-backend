package com.spe.ecuavoley.dto;

public class JugadorEquipoResponse {

    private Long id;
    private String nombre;
    private String fotoUrl;
    private String posicion;

    public JugadorEquipoResponse() {
    }

    public JugadorEquipoResponse(
            Long id,
            String nombre,
            String fotoUrl,
            String posicion) {

        this.id = id;
        this.nombre = nombre;
        this.fotoUrl = fotoUrl;
        this.posicion = posicion;
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

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
}