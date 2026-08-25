package com.spe.ecuavoley.dto;

public class JugadorEstadisticaResponse {

    private Long jugadorId;
    private String nombre;
    private String apodo;

    private long partidosJugados;
    private long partidosGanados;
    private long partidosPerdidos;
    private long mvp;

    private double porcentajeVictorias;
    private String fotoUrl;

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public JugadorEstadisticaResponse() {
    }

    public Long getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(Long jugadorId) {
        this.jugadorId = jugadorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
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

    public long getMvp() {
        return mvp;
    }

    public void setMvp(long mvp) {
        this.mvp = mvp;
    }

    public double getPorcentajeVictorias() {
        return porcentajeVictorias;
    }

    public void setPorcentajeVictorias(
            double porcentajeVictorias) {

        this.porcentajeVictorias = porcentajeVictorias;
    }
}