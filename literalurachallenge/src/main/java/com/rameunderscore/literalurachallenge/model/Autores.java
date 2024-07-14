package com.rameunderscore.literalurachallenge.model;

import jakarta.persistence.*;

@Entity
@Table (name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int fechaNacimiento;
    private int fechaFallecimiento;
    private String nombreAutor;
    @ManyToOne
    private Libro libro;

    public Autores(){}

    public Autores(DatosAutores datosAutores){
        this.fechaNacimiento = datosAutores.fechaNacimiento();
        this.fechaFallecimiento = datosAutores.fechaFallecimiento();
        this.nombreAutor = datosAutores.nombreAutor();
    }

    @Override
    public String toString() {
        return  nombreAutor + "(" + fechaNacimiento + "-" + fechaFallecimiento + ")";
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(int fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
