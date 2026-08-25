package com.spe.ecuavoley.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "partidos")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String equipoA;

    @Column(nullable = false)
    private String equipoB;

    @Column(nullable = false)
    private int puntosA = 0;

    @Column(nullable = false)
    private int puntosB = 0;

    @Column(nullable = false)
    private int setsA = 0;

    @Column(nullable = false)
    private int setsB = 0;

    @Column(nullable = false)
    private int setActual = 1;

    @Column(nullable = false)
    private int metaPuntos;

    @Column(nullable = false)
    private String equipoCambio = "A";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPartido estado = EstadoPartido.EN_JUEGO;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "hora_programada")
    private LocalTime horaProgramada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campeonato_id")
    private Campeonato campeonato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancha_id")
    private Cancha cancha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_a_id")
    private Equipo equipoAEntidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_b_id")
    private Equipo equipoBEntidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fecha_campeonato_id")
    private FechaCampeonato fechaCampeonato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mvp_jugador_id")
    private Jugador mvpJugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_ganador_id")
    private Equipo equipoGanador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @PrePersist
    @PreUpdate
    public void actualizarFecha() {
        fechaActualizacion = LocalDateTime.now();
    }

    public Partido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    public Equipo getEquipoAEntidad() {
        return equipoAEntidad;
    }

    public void setEquipoAEntidad(Equipo equipoAEntidad) {
        this.equipoAEntidad = equipoAEntidad;
    }

    public Equipo getEquipoBEntidad() {
        return equipoBEntidad;
    }

    public void setEquipoBEntidad(Equipo equipoBEntidad) {
        this.equipoBEntidad = equipoBEntidad;
    }

    public FechaCampeonato getFechaCampeonato() {
        return fechaCampeonato;
    }

    public void setFechaCampeonato(
            FechaCampeonato fechaCampeonato) {

        this.fechaCampeonato = fechaCampeonato;
    }

    public Jugador getMvpJugador() {
        return mvpJugador;
    }

    public void setMvpJugador(Jugador mvpJugador) {
        this.mvpJugador = mvpJugador;
    }

    public Equipo getEquipoGanador() {
        return equipoGanador;
    }

    public void setEquipoGanador(Equipo equipoGanador) {
        this.equipoGanador = equipoGanador;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public LocalTime getHoraProgramada() {
        return horaProgramada;
    }

    public void setHoraProgramada(LocalTime horaProgramada) {
        this.horaProgramada = horaProgramada;
    }
}