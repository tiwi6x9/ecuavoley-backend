package com.spe.ecuavoley.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProximoPartidoResponse {

    private Long id;

    private Long campeonatoId;
    private String campeonatoNombre;

    private Integer fechaNumero;
    private LocalDate fecha;

    private LocalTime horaProgramada;

    private Long equipoAId;
    private String equipoA;

    private Long equipoBId;
    private String equipoB;

    private String canchaNombre;

    public ProximoPartidoResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCampeonatoNombre(
            String campeonatoNombre) {

        this.campeonatoNombre = campeonatoNombre;
    }

    public Integer getFechaNumero() {
        return fechaNumero;
    }

    public void setFechaNumero(
            Integer fechaNumero) {

        this.fechaNumero = fechaNumero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraProgramada() {
        return horaProgramada;
    }

    public void setHoraProgramada(
            LocalTime horaProgramada) {

        this.horaProgramada = horaProgramada;
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

    public String getCanchaNombre() {
        return canchaNombre;
    }

    public void setCanchaNombre(
            String canchaNombre) {

        this.canchaNombre = canchaNombre;
    }
}