package com.spe.ecuavoley.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "logo_url")
    private String logoUrl;

    // Galería de fotos del equipo (partidos jugados, MVPs, etc.).
    // Opcionales: el perfil se ve igual si no se han cargado todavía.
    @Column(name = "foto_1_url")
    private String foto1Url;

    @Column(name = "foto_2_url")
    private String foto2Url;

    @Column(name = "foto_3_url")
    private String foto3Url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dirigente_id")
    private Dirigente dirigente;

    public Equipo() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getFoto1Url() {
        return foto1Url;
    }

    public void setFoto1Url(String foto1Url) {
        this.foto1Url = foto1Url;
    }

    public String getFoto2Url() {
        return foto2Url;
    }

    public void setFoto2Url(String foto2Url) {
        this.foto2Url = foto2Url;
    }

    public String getFoto3Url() {
        return foto3Url;
    }

    public void setFoto3Url(String foto3Url) {
        this.foto3Url = foto3Url;
    }

    public Dirigente getDirigente() {
        return dirigente;
    }

    public void setDirigente(Dirigente dirigente) {
        this.dirigente = dirigente;
    }
}
