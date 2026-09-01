package com.spe.ecuavoley.dto;

public class EquipoCampeonatoResponse {

    private Long id;
    private String nombre;
    private String logoUrl;

    private Long dirigenteId;
    private String dirigenteNombre;
    private String dirigenteTelefono;
    private String dirigenteFotoUrl;

    public EquipoCampeonatoResponse() {
    }

    public EquipoCampeonatoResponse(
            Long id,
            String nombre,
            String logoUrl,
            Long dirigenteId,
            String dirigenteNombre,
            String dirigenteTelefono,
            String dirigenteFotoUrl) {

        this.id = id;
        this.nombre = nombre;
        this.logoUrl = logoUrl;

        this.dirigenteId = dirigenteId;
        this.dirigenteNombre = dirigenteNombre;
        this.dirigenteTelefono = dirigenteTelefono;
        this.dirigenteFotoUrl = dirigenteFotoUrl;
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

    public Long getDirigenteId() {
        return dirigenteId;
    }

    public String getDirigenteNombre() {
        return dirigenteNombre;
    }

    public String getDirigenteTelefono() {
        return dirigenteTelefono;
    }

    public String getDirigenteFotoUrl() {
        return dirigenteFotoUrl;
    }
}