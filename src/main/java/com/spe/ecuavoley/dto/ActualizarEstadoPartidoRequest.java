package com.spe.ecuavoley.dto;

import com.spe.ecuavoley.model.EstadoPartido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ActualizarEstadoPartidoRequest {

    @Min(value = 0, message = "puntosA no puede ser negativo")
    private int puntosA;

    @Min(value = 0, message = "puntosB no puede ser negativo")
    private int puntosB;

    @Min(value = 0, message = "setsA no puede ser negativo")
    private int setsA;

    @Min(value = 0, message = "setsB no puede ser negativo")
    private int setsB;

    @Min(value = 1, message = "setActual debe ser al menos 1")
    private int setActual;

    @Min(value = 1, message = "metaPuntos debe ser mayor a 0")
    private int metaPuntos;

    @NotBlank(message = "equipoCambio es obligatorio")
    @Pattern(regexp = "A|B", message = "equipoCambio debe ser 'A' o 'B'")
    private String equipoCambio;

    @NotNull(message = "estado es obligatorio")
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
