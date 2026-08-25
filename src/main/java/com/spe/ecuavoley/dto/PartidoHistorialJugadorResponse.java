package com.spe.ecuavoley.dto;

import java.time.LocalDateTime;

public class PartidoHistorialJugadorResponse {

    private Long partidoId;

    private String equipoPropio;
    private String equipoRival;

    private int setsPropio;
    private int setsRival;

    private String resultado;

    private boolean mvp;

    private LocalDateTime fecha;

    public PartidoHistorialJugadorResponse() {
    }

    public Long getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }

    public String getEquipoPropio() {
        return equipoPropio;
    }

    public void setEquipoPropio(String equipoPropio) {
        this.equipoPropio = equipoPropio;
    }

    public String getEquipoRival() {
        return equipoRival;
    }

    public void setEquipoRival(String equipoRival) {
        this.equipoRival = equipoRival;
    }

    public int getSetsPropio() {
        return setsPropio;
    }

    public void setSetsPropio(int setsPropio) {
        this.setsPropio = setsPropio;
    }

    public int getSetsRival() {
        return setsRival;
    }

    public void setSetsRival(int setsRival) {
        this.setsRival = setsRival;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public boolean isMvp() {
        return mvp;
    }

    public void setMvp(boolean mvp) {
        this.mvp = mvp;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}