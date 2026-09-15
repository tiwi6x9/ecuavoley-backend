package com.spe.ecuavoley.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class SeleccionarJugadoresPartidoRequest {

    @NotEmpty(message = "jugadoresEquipoA no puede estar vacio")
    private List<Long> jugadoresEquipoA;

    @NotEmpty(message = "jugadoresEquipoB no puede estar vacio")
    private List<Long> jugadoresEquipoB;

    public List<Long> getJugadoresEquipoA() {
        return jugadoresEquipoA;
    }

    public void setJugadoresEquipoA(
            List<Long> jugadoresEquipoA) {

        this.jugadoresEquipoA =
                jugadoresEquipoA;
    }

    public List<Long> getJugadoresEquipoB() {
        return jugadoresEquipoB;
    }

    public void setJugadoresEquipoB(
            List<Long> jugadoresEquipoB) {

        this.jugadoresEquipoB =
                jugadoresEquipoB;
    }
}
