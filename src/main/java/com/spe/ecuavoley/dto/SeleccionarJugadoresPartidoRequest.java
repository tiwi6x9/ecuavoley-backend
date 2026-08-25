package com.spe.ecuavoley.dto;

import java.util.List;

public class SeleccionarJugadoresPartidoRequest {

    private List<Long> jugadoresEquipoA;
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