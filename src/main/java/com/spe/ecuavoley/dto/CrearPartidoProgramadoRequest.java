package com.spe.ecuavoley.dto;

import java.time.LocalTime;

public class CrearPartidoProgramadoRequest {

    private Long campeonatoId;
    private Long canchaId;
    private Long equipoAId;
    private Long equipoBId;
    private int metaPuntos;

    private LocalTime horaProgramada;

    public LocalTime getHoraProgramada() {
        return horaProgramada;
    }

    public void setHoraProgramada(
            LocalTime horaProgramada) {

        this.horaProgramada = horaProgramada;
    }

    public Long getCampeonatoId() {
        return campeonatoId;
    }

    public void setCampeonatoId(Long campeonatoId) {
        this.campeonatoId = campeonatoId;
    }

    public Long getCanchaId() {
        return canchaId;
    }

    public void setCanchaId(Long canchaId) {
        this.canchaId = canchaId;
    }

    public Long getEquipoAId() {
        return equipoAId;
    }

    public void setEquipoAId(Long equipoAId) {
        this.equipoAId = equipoAId;
    }

    public Long getEquipoBId() {
        return equipoBId;
    }

    public void setEquipoBId(Long equipoBId) {
        this.equipoBId = equipoBId;
    }

    public int getMetaPuntos() {
        return metaPuntos;
    }

    public void setMetaPuntos(int metaPuntos) {
        this.metaPuntos = metaPuntos;
    }

    private Long fechaCampeonatoId;

    public Long getFechaCampeonatoId() {
        return fechaCampeonatoId;
    }

    public void setFechaCampeonatoId(
            Long fechaCampeonatoId) {

        this.fechaCampeonatoId = fechaCampeonatoId;
    }
}