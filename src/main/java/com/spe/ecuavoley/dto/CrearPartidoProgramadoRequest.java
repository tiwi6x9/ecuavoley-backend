package com.spe.ecuavoley.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CrearPartidoProgramadoRequest {

    @NotNull(message = "campeonatoId es obligatorio")
    private Long campeonatoId;

    private Long canchaId;

    @NotNull(message = "equipoAId es obligatorio")
    private Long equipoAId;

    @NotNull(message = "equipoBId es obligatorio")
    private Long equipoBId;

    @Min(value = 1, message = "metaPuntos debe ser mayor a 0")
    private int metaPuntos;

    private LocalTime horaProgramada;

    @NotNull(message = "fechaCampeonatoId es obligatorio")
    private Long fechaCampeonatoId;

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

    public Long getFechaCampeonatoId() {
        return fechaCampeonatoId;
    }

    public void setFechaCampeonatoId(
            Long fechaCampeonatoId) {

        this.fechaCampeonatoId = fechaCampeonatoId;
    }
}
