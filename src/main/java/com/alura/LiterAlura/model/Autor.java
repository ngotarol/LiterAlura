package com.alura.LiterAlura.model;

import com.alura.LiterAlura.dto.DatosAutor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private int birthYear;

    private int deathYear;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Libro> libros;

    public Autor(){}

    public Autor(Long id, String name, int birthYear, int deathYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public List<String> getLibros() {
        List<String> titulos = new ArrayList<>();
        libros.forEach(libro -> titulos.add(libro.getTitulo()));

        return titulos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString(){
        return "\n\tAutor: " + name +
                "\n\tAño de nacimiento: " + birthYear +
                "\n\tAño de defuncion: " + deathYear;
    }

    public String toStringLibros(){
        return "\n\tAutor: " + name +
                "\n\tAño de nacimiento: " + birthYear +
                "\n\tAño de defuncion: " + deathYear +
                "\n\tLibros: " + getLibros();
    }

    public Autor(DatosAutor datosAutor){
        this.name = datosAutor.name();
        this.birthYear = datosAutor.birth_year();
        this.deathYear = datosAutor.death_year();
    }
}
