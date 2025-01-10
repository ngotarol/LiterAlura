package com.alura.LiterAlura.dto;

import com.alura.LiterAlura.model.IdiomaEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        Long id,
        String title,
        List<DatosAutor>  authors,
        List<String> subjects,
        List<String> bookshelves,
        List<IdiomaEntity> languages,
        String detail,
        Long download_count
) {}
