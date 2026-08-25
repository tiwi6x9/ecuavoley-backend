package com.spe.ecuavoley.dto;

import java.time.LocalDate;

public class ResultadoPartidoResponse {

    private Long id;

    private Integer fechaNumero;
    private LocalDate fecha;

    private Long equipoAId;
    private String equipoA;

    private Long equipoBId;
    private String equipoB;

    private int setsA;
    private int setsB;

    private String canchaNombre;

    public ResultadoPartidoResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getEquipoAId() {
        return equipoAId;
    }

    public void setEquipoAId(Long equipoAId) {
        this.equipoAId = equipoAId;
    }

    public String getEquipoA() {
        return equipoA;
    }

    public void setEquipoA(String equipoA) {
        this.equipoA = equipoA;
    }

    public Long getEquipoBId() {
        return equipoBId;
    }

    public void setEquipoBId(Long equipoBId) {
        this.equipoBId = equipoBId;
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