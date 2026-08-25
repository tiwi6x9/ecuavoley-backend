package com.spe.ecuavoley.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "serie_equipos",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "serie_id",
                                "equipo_id"
                        })
        })
public class SerieEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "serie_id",
            nullable = false)
    private Serie serie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "equipo_id",
            nullable = false)
    private Equipo equipo;

    public SerieEquipo() {
    }

    public Long getId() {
        return id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}