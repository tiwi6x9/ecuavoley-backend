package com.spe.ecuavoley.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "partido_jugadores",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "partido_id",
                                "jugador_id"
                        })
        })
public class PartidoJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "partido_id",
            nullable = false)
    private Partido partido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "jugador_id",
            nullable = false)
    private Jugador jugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "equipo_id",
            nullable = false)
    private Equipo equipo;

    public PartidoJugador() {
    }

    public Long getId() {
        return id;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}