package com.spe.ecuavoley.dto;

import jakarta.validation.constraints.NotNull;

public class SeleccionarMvpRequest {

    @NotNull(message = "jugadorId es obligatorio")
    private Long jugadorId;

    public Long getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(Long jugadorId) {
        this.jugadorId = jugadorId;
    }
}
