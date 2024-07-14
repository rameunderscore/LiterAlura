package com.rameunderscore.literalurachallenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutores> listaDeAutores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double totalDescargas
) {
}
