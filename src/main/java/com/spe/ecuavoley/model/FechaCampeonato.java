package com.spe.ecuavoley.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "fechas_campeonato")
public class FechaCampeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "campeonato_id",
            nullable = false)
    private Campeonato campeonato;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false)
    private LocalDate fecha;

    public FechaCampeonato() {
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}