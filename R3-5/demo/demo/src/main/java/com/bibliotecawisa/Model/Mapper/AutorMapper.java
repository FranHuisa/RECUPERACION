package com.bibliotecawisa.Model.Mapper;

import com.bibliotecawisa.Model.DTO.AutorCreateDTO;
import com.bibliotecawisa.Model.DTO.AutorUpdateDTO;
import com.bibliotecawisa.Model.Entidad.Autor;
import com.bibliotecawisa.Model.Resource.AutorResource;
import org.springframework.stereotype.Component;
 
@Component
public class AutorMapper {
 
    public Autor toEntity(AutorCreateDTO dto) {
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setFechaNacimiento(dto.getFechaNacimiento());
        return autor;
    }
 
    public AutorResource toResource(Autor autor) {
        AutorResource resource = new AutorResource();
        resource.setId(autor.getId());
        resource.setNombre(autor.getNombre());
        resource.setApellido(autor.getApellido());
        resource.setNacionalidad(autor.getNacionalidad());
        resource.setFechaNacimiento(autor.getFechaNacimiento());
        resource.setTotalLibros(autor.getLibros().size());
        return resource;
    }
 
    public void updateEntityFromDTO(AutorUpdateDTO dto, Autor autor) {
        if (dto.getNombre() != null) autor.setNombre(dto.getNombre());
        if (dto.getApellido() != null) autor.setApellido(dto.getApellido());
        if (dto.getNacionalidad() != null) autor.setNacionalidad(dto.getNacionalidad());
        if (dto.getFechaNacimiento() != null) autor.setFechaNacimiento(dto.getFechaNacimiento());
    }
}