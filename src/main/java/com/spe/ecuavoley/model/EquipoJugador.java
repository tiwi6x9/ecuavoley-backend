package com.spe.ecuavoley.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "equipo_jugadores",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "campeonato_id",
                                "equipo_id",
                                "jugador_id"
                        })
        })
public class EquipoJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "campeonato_id",
            nullable = false)
    private Campeonato campeonato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "equipo_id",
            nullable = false)
    private Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "jugador_id",
            nullable = false)
    private Jugador jugador;

    public EquipoJugador() {
    }

    public Long getId() {
        return id;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}