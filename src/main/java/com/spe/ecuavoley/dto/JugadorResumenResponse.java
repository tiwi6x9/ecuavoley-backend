package com.spe.ecuavoley.dto;

public class JugadorResumenResponse {

    private Long id;
    private String nombre;
    private String apodo;
    private String fotoUrl;

    public JugadorResumenResponse() {
    }

    public JugadorResumenResponse(
            Long id,
            String nombre,
            String apodo,
            String fotoUrl) {

        this.id = id;
        this.nombre = nombre;
        this.apodo = apodo;
        this.fotoUrl = fotoUrl;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}