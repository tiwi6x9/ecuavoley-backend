package com.spe.ecuavoley.dto;

import java.time.LocalDateTime;

public class PartidoHistorialEquipoResponse {

    private Long partidoId;

    private String equipo;
    private String rival;

    private int setsEquipo;
    private int setsRival;

    private String resultado;

    private LocalDateTime fecha;

    public PartidoHistorialEquipoResponse() {
    }

    public Long getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getRival() {
        return rival;
    }

    public void setRival(String rival) {
        this.rival = rival;
    }

    public int getSetsEquipo() {
        return setsEquipo;
    }

    public void setSetsEquipo(int setsEquipo) {
        this.setsEquipo = setsEquipo;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}