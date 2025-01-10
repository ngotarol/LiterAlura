package com.alura.LiterAlura.repository;

import com.alura.LiterAlura.model.IdiomaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IdiomaRepository extends JpaRepository<IdiomaEntity,Long> {

    Optional<IdiomaEntity> findByNombreIgnoreCase(String idioma);

    List<IdiomaEntity> findAll();
}
