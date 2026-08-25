package com.spe.ecuavoley.dto;

import java.time.LocalDate;

public class PartidoEnVivoResponse {

    private Long id;

    private String equipoA;
    private String equipoB;

    private int puntosA;
    private int puntosB;

    private int setsA;
    private int setsB;

    private int setActual;
    private int metaPuntos;

    private String equipoCambio;
    private String estado;

    private String tipo;

    private Long campeonatoId;
    private String campeonatoNombre;

    private Integer fechaNumero;
    private LocalDate fecha;

    private String canchaNombre;

    public PartidoEnVivoResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getPuntosA() {
        return puntosA;
    }

    public void setPuntosA(int puntosA) {
        this.puntosA = puntosA;
    }

    public int getPuntosB() {
        return puntosB;
    }

    public void setPuntosB(int puntosB) {
        this.puntosB = puntosB;
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

    public int getSetActual() {
        return setActual;
    }

    public void setSetActual(int setActual) {
        this.setActual = setActual;
    }

    public int getMetaPuntos() {
        return metaPuntos;
    }

    public void setMetaPuntos(int metaPuntos) {
        this.metaPuntos = metaPuntos;
    }

    public String getEquipoCambio() {
        return equipoCambio;
    }

    public void setEquipoCambio(String equipoCambio) {
        this.equipoCambio = equipoCambio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getCanchaNombre() {
        return canchaNombre;
    }

    public void setCanchaNombre(String canchaNombre) {
        this.canchaNombre = canchaNombre;
    }
}