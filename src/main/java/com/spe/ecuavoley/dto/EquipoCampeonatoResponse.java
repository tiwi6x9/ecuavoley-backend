package com.spe.ecuavoley.dto;

public class EquipoCampeonatoResponse {

    private Long id;
    private String nombre;
    private String logoUrl;

    public EquipoCampeonatoResponse() {
    }

    public EquipoCampeonatoResponse(
            Long id,
            String nombre,
            String logoUrl) {

        this.id = id;
        this.nombre = nombre;
        this.logoUrl = logoUrl;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}