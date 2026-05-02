package com.bibliotecawisa.Model.Mapper;

import com.bibliotecawisa.Model.DTO.LibroCreateDTO;
import com.bibliotecawisa.Model.DTO.LibroUpdateDTO;
import com.bibliotecawisa.Model.Entidad.Autor;
import com.bibliotecawisa.Model.Entidad.Libro;
import com.bibliotecawisa.Model.Resource.LibroResource;
import org.springframework.stereotype.Component;
 
@Component
public class LibroMapper {
 
    public Libro toEntity(LibroCreateDTO dto, Autor autor) {
        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setIsbn(dto.getIsbn());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setStockTotal(dto.getStockTotal());
        libro.setStockDisponible(dto.getStockTotal());
        libro.setGenero(dto.getGenero());
        libro.setAutor(autor);
        return libro;
    }
 
    public LibroResource toResource(Libro libro) {
        LibroResource resource = new LibroResource();
        resource.setId(libro.getId());
        resource.setTitulo(libro.getTitulo());
        resource.setIsbn(libro.getIsbn());
        resource.setAnioPublicacion(libro.getAnioPublicacion());
        resource.setStockTotal(libro.getStockTotal());
        resource.setStockDisponible(libro.getStockDisponible());
        resource.setGenero(libro.getGenero());
        resource.setAutorId(libro.getAutor().getId());
        resource.setNombreAutor(libro.getAutor().getNombre() + " " + libro.getAutor().getApellido());
        return resource;
    }
 
    public void updateEntityFromDTO(LibroUpdateDTO dto, Libro libro, Autor autor) {
        if (dto.getTitulo() != null) libro.setTitulo(dto.getTitulo());
        if (dto.getAnioPublicacion() != null) libro.setAnioPublicacion(dto.getAnioPublicacion());
        if (dto.getGenero() != null) libro.setGenero(dto.getGenero());
        if (dto.getStockTotal() != null) libro.setStockTotal(dto.getStockTotal());
        if (autor != null) libro.setAutor(autor);
    }
}