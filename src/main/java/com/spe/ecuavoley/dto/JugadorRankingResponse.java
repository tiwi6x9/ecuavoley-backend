package com.spe.ecuavoley.dto;

public class JugadorRankingResponse {

    private Long jugadorId;
    private String nombre;
    private String apodo;

    private long partidosJugados;
    private long partidosGanados;
    private long mvp;

    private double porcentajeVictorias;
    private long puntaje;

    private String fotoUrl;

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public JugadorRankingResponse() {
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

    public long getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(long puntaje) {
        this.puntaje = puntaje;
    }
}