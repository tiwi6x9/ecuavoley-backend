package com.spe.ecuavoley.dto;

import com.spe.ecuavoley.model.EstadoPartido;

public class ActualizarEstadoPartidoRequest {

    private int puntosA;
    private int puntosB;
    private int setsA;
    private int setsB;
    private int setActual;
    private int metaPuntos;
    private String equipoCambio;
    private EstadoPartido estado;

    public ActualizarEstadoPartidoRequest() {
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

    public EstadoPartido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }
}