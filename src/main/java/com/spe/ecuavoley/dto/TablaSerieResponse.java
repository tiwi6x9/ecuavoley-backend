package com.spe.ecuavoley.dto;

public class TablaSerieResponse {

    private Long equipoId;
    private String equipoNombre;
    private String logoUrl;

    private long partidosJugados;
    private long partidosGanados;
    private long partidosPerdidos;

    private long setsGanados;
    private long setsPerdidos;
    private long diferenciaSets;

    private long puntos;

    public TablaSerieResponse() {
    }

    public Long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }

    public String getEquipoNombre() {
        return equipoNombre;
    }

    public void setEquipoNombre(String equipoNombre) {
        this.equipoNombre = equipoNombre;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public long getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(long partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public long getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(long partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public long getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public void setPartidosPerdidos(long partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    public long getSetsGanados() {
        return setsGanados;
    }

    public void setSetsGanados(long setsGanados) {
        this.setsGanados = setsGanados;
    }

    public long getSetsPerdidos() {
        return setsPerdidos;
    }

    public void setSetsPerdidos(long setsPerdidos) {
        this.setsPerdidos = setsPerdidos;
    }

    public long getDiferenciaSets() {
        return diferenciaSets;
    }

    public void setDiferenciaSets(long diferenciaSets) {
        this.diferenciaSets = diferenciaSets;
    }

    public long getPuntos() {
        return puntos;
    }

    public void setPuntos(long puntos) {
        this.puntos = puntos;
    }
}