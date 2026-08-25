package com.spe.ecuavoley.dto;

import java.time.LocalDate;

public class HistorialPartidoResponse {

    private Long id;

    private String tipo;

    private Long campeonatoId;
    private String campeonatoNombre;

    private Integer fechaNumero;
    private LocalDate fecha;

    private String equipoA;
    private String equipoB;

    private int setsA;
    private int setsB;

    private String canchaNombre;

    public HistorialPartidoResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getCampeonatoId() {
        return campeonatoId;
    }

    public void setCampeonatoId(Long campeonatoId) {
        this.campeonatoId = campeonatoId;
    }

    public String getCampeonatoNombre() {
        return campeonatoNombre;
    }

    public void setCampeonatoNombre(String campeonatoNombre) {
        this.campeonatoNombre = campeonatoNombre;
    }

    public Integer getFechaNumero() {
        return fechaNumero;
    }

    public void setFechaNumero(Integer fechaNumero) {
        this.fechaNumero = fechaNumero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEquipoA() {
        return equipoA;
    }

    public void setEquipoA(String equipoA) {
        this.equipoA = equipoA;
    }

    public String getEquipoB() {
        return equipoB;
    }

    public void setEquipoB(String equipoB) {
        this.equipoB = equipoB;
    }

    public int getSetsA() {
        return setsA;
    }

    public void setSetsA(int setsA) {
        this.setsA = setsA;
    }

    public int getSetsB() {
        return setsB;
    }

    public void setSetsB(int setsB) {
        this.setsB = setsB;
    }

    public String getCanchaNombre() {
        return canchaNombre;
    }

    public void setCanchaNombre(String canchaNombre) {
        this.canchaNombre = canchaNombre;
    }
}