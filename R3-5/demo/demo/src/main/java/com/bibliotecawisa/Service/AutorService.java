package com.bibliotecawisa.Service;

import com.bibliotecawisa.Exception.ConflictoException;
import com.bibliotecawisa.Exception.RecursoNoEncontradoException;
import com.bibliotecawisa.Model.DTO.AutorCreateDTO;
import com.bibliotecawisa.Model.DTO.AutorUpdateDTO;
import com.bibliotecawisa.Model.Entidad.Autor;
import com.bibliotecawisa.Model.Mapper.AutorMapper;
import com.bibliotecawisa.Model.Resource.AutorResource;
import com.bibliotecawisa.Exception.ReglaNegocioException;
import com.bibliotecawisa.Repository.AutorRepository;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
public class AutorService {
 
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;
 
    public AutorService(AutorRepository autorRepository, AutorMapper autorMapper) {
        this.autorRepository = autorRepository;
        this.autorMapper = autorMapper;
    }
 
    public List<AutorResource> findAll() {
        return autorRepository.findAll().stream()
                .map(autorMapper::toResource)
                .collect(Collectors.toList());
    }
 
    public AutorResource findById(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con id: " + id));
        return autorMapper.toResource(autor);
    }
 
    public AutorResource create(AutorCreateDTO dto) {
        autorRepository.findByNombreAndApellido(dto.getNombre(), dto.getApellido())
                .ifPresent(a -> { throw new ConflictoException("Ya existe un autor con ese nombre y apellido"); });
        Autor autor = autorMapper.toEntity(dto);
        return autorMapper.toResource(autorRepository.save(autor));
    }
 
    public AutorResource update(Long id, AutorCreateDTO dto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con id: " + id));
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setFechaNacimiento(dto.getFechaNacimiento());
        return autorMapper.toResource(autorRepository.save(autor));
    }
 
    public AutorResource partialUpdate(Long id, AutorUpdateDTO dto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con id: " + id));
        autorMapper.updateEntityFromDTO(dto, autor);
        return autorMapper.toResource(autorRepository.save(autor));
    }
 
    public void delete(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con id: " + id));
        if (!autor.getLibros().isEmpty()) {
            throw new ReglaNegocioException("No se puede eliminar un autor que tiene libros registrados");
        }
        autorRepository.deleteById(id);
    }
 
    public List<AutorResource> findByNacionalidad(String nacionalidad) {
        return autorRepository.findByNacionalidad(nacionalidad).stream()
                .map(autorMapper::toResource)
                .collect(Collectors.toList());
    }
 
    public List<AutorResource> buscar(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre).stream()
                .map(autorMapper::toResource)
                .collect(Collectors.toList());
    }
}