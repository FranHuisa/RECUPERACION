package com.bibliotecawisa.Service;

import com.bibliotecawisa.Exception.ConflictoException;
import com.bibliotecawisa.Exception.RecursoNoEncontradoException;
import com.bibliotecawisa.Model.DTO.LibroCreateDTO;
import com.bibliotecawisa.Model.DTO.LibroUpdateDTO;
import com.bibliotecawisa.Model.Entidad.Autor;
import com.bibliotecawisa.Model.Entidad.Libro;
import com.bibliotecawisa.Model.Mapper.LibroMapper;
import com.bibliotecawisa.Model.Resource.LibroResource;
import com.bibliotecawisa.Repository.AutorRepository;
import com.bibliotecawisa.Repository.LibroRepository;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
public class LibroService {
 
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final LibroMapper libroMapper;
 
    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository, LibroMapper libroMapper) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.libroMapper = libroMapper;
    }
 
    public List<LibroResource> findAll() {
        return libroRepository.findAll().stream()
                .map(libroMapper::toResource)
                .collect(Collectors.toList());
    }
 
    public LibroResource findById(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id: " + id));
        return libroMapper.toResource(libro);
    }
 
    public LibroResource create(LibroCreateDTO dto) {
        libroRepository.findByIsbn(dto.getIsbn())
                .ifPresent(l -> { throw new ConflictoException("Ya existe un libro con el ISBN: " + dto.getIsbn()); });
        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con id: " + dto.getAutorId()));
        Libro libro = libroMapper.toEntity(dto, autor);
        return libroMapper.toResource(libroRepository.save(libro));
    }
 
    public LibroResource update(Long id, LibroCreateDTO dto) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id: " + id));
        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con id: " + dto.getAutorId()));
        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setStockTotal(dto.getStockTotal());
        libro.setGenero(dto.getGenero());
        libro.setAutor(autor);
        return libroMapper.toResource(libroRepository.save(libro));
    }
 
    public LibroResource partialUpdate(Long id, LibroUpdateDTO dto) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id: " + id));
        Autor autor = null;
        if (dto.getAutorId() != null) {
            autor = autorRepository.findById(dto.getAutorId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con id: " + dto.getAutorId()));
        }
        libroMapper.updateEntityFromDTO(dto, libro, autor);
        return libroMapper.toResource(libroRepository.save(libro));
    }
 
    public void delete(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Libro no encontrado con id: " + id);
        }
        libroRepository.deleteById(id);
    }
 
    public List<LibroResource> findByGenero(String genero) {
        return libroRepository.findByGenero(genero).stream()
                .map(libroMapper::toResource)
                .collect(Collectors.toList());
    }
 
    public List<LibroResource> buscar(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(libroMapper::toResource)
                .collect(Collectors.toList());
    }
 
    public List<LibroResource> findDisponibles() {
        return libroRepository.findByStockDisponibleGreaterThan(0).stream()
                .map(libroMapper::toResource)
                .collect(Collectors.toList());
    }
}