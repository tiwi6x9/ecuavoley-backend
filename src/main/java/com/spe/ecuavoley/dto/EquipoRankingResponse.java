package com.spe.ecuavoley.dto;

public class EquipoRankingResponse {

    private Long equipoId;
    private String nombre;

    private long partidosJugados;
    private long partidosGanados;
    private long partidosPerdidos;

    private long setsGanados;
    private long setsPerdidos;

    private double porcentajeVictorias;
    private long puntaje;

    private String logoUrl;

    private Long dirigenteId;
    private String dirigenteNombre;
    private String dirigenteTelefono;
    private String dirigenteFotoUrl;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public EquipoRankingResponse() {
    }

    public Long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Long getDirigenteId() {
        return dirigenteId;
    }

    public void setDirigenteId(Long dirigenteId) {
        this.dirigenteId = dirigenteId;
    }

    public String getDirigenteNombre() {
        return dirigenteNombre;
    }

    public void setDirigenteNombre(String dirigenteNombre) {
        this.dirigenteNombre = dirigenteNombre;
    }

    public String getDirigenteTelefono() {
        return dirigenteTelefono;
    }

    public void setDirigenteTelefono(String dirigenteTelefono) {
        this.dirigenteTelefono = dirigenteTelefono;
    }

    public String getDirigenteFotoUrl() {
        return dirigenteFotoUrl;
    }

    public void setDirigenteFotoUrl(String dirigenteFotoUrl) {
        this.dirigenteFotoUrl = dirigenteFotoUrl;
    }
}