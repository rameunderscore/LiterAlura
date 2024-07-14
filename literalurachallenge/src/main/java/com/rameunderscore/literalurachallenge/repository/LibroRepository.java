package com.rameunderscore.literalurachallenge.repository;

import com.rameunderscore.literalurachallenge.model.Autores;
import com.rameunderscore.literalurachallenge.model.Idioma;
import com.rameunderscore.literalurachallenge.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);
@Query("SELECT l FROM Libro a JOIN a.autor l")
    List<Autores> findByAutor();
@Query("SELECT l FROM Libro a JOIN a.autor l WHERE l.fechaNacimiento <= :anhio AND l.fechaFallecimiento >= :anhio")
    List<Autores> filtarFechaAutor(String anhio);

    List<Libro> findByIdiomas(Idioma idioma);
}