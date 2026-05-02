package com.bibliotecawisa.Repository;

import com.bibliotecawisa.Model.Entidad.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(String isbn);

    List<Libro> findByGenero(String genero);

    List<Libro> findByAutorId(Long autorId);

    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findByStockDisponibleGreaterThan(int stock);
}
