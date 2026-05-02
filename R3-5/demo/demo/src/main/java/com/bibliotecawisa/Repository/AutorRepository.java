package com.bibliotecawisa.Repository;

import com.bibliotecawisa.Model.Entidad.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByNacionalidad(String nacionalidad);

    List<Autor> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    Optional<Autor> findByNombreAndApellido(String nombre, String apellido);
}