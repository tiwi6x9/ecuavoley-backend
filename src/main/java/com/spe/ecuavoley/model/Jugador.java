package com.spe.ecuavoley.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String apodo;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "posicion")
    private String posicion;

    // Galería de fotos del jugador (partidos jugados, MVPs, etc.),
    // separada de la foto de perfil (fotoUrl). Opcionales.
    @Column(name = "foto_1_url")
    private String foto1Url;

    @Column(name = "foto_2_url")
    private String foto2Url;

    @Column(name = "foto_3_url")
    private String foto3Url;

    public Jugador() {
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

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
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
}
