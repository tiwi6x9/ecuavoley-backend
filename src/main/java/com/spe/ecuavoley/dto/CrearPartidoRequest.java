package com.spe.ecuavoley.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Datos permitidos para crear un partido "libre" (sin campeonato),
 * usado por la pantalla de marcador rapido de la app.
 *
 * Antes, el endpoint POST /api/partidos aceptaba la entidad Partido
 * completa como cuerpo de la peticion, lo que permitia a cualquier
 * cliente enviar campos que no deberian ser asignables desde afuera
 * (id, estado, fechaActualizacion, relaciones con campeonato/equipo,
 * etc.). Este DTO limita la entrada a exactamente lo que la app
 * envia hoy.
 */
public class CrearPartidoRequest {

    @NotBlank(message = "equipoA es obligatorio")
    private String equipoA;

    @NotBlank(message = "equipoB es obligatorio")
    private String equipoB;

    @Min(value = 1, message = "metaPuntos debe ser mayor a 0")
    private int metaPuntos;

    public CrearPartidoRequest() {
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

    public int getMetaPuntos() {
        return metaPuntos;
    }

    public void setMetaPuntos(int metaPuntos) {
        this.metaPuntos = metaPuntos;
    }
}
