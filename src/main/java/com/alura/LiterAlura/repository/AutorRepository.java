package com.alura.LiterAlura.repository;

import com.alura.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    Optional<Autor> findByNameIgnoreCase(String name);

    List<Autor> findAll();

    @Query("SELECT a FROM Autor a WHERE :anio >= a.birthYear AND :anio <= a.deathYear")
    List<Autor> autoresVivosPorAnio(Integer anio);

    List<Autor> findByNameContainingIgnoreCase(String nombre);
}
