package com.alura.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "idiomas")
public class IdiomaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    public IdiomaEntity(){}

    public IdiomaEntity(String nombre){
        this.nombre = nombre;
    }

    public IdiomaEntity(IdiomaEntity idiomaEntity) {
        this.nombre = idiomaEntity.getNombre();
    }

    public IdiomaEntity(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
