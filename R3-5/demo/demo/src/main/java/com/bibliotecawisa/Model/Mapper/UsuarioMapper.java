package com.bibliotecawisa.Model.Mapper;

import com.bibliotecawisa.Model.DTO.UsuarioCreateDTO;
import com.bibliotecawisa.Model.DTO.UsuarioUpdateDTO;
import com.bibliotecawisa.Model.Entidad.Prestamo;
import com.bibliotecawisa.Model.Entidad.Usuario;
import com.bibliotecawisa.Model.Resource.UsuarioResource;
import org.springframework.stereotype.Component;
 
@Component
public class UsuarioMapper {
 
    public Usuario toEntity(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setActivo(true);
        return usuario;
    }
 
    public UsuarioResource toResource(Usuario usuario) {
        UsuarioResource resource = new UsuarioResource();
        resource.setId(usuario.getId());
        resource.setNombre(usuario.getNombre());
        resource.setApellido(usuario.getApellido());
        resource.setEmail(usuario.getEmail());
        resource.setTelefono(usuario.getTelefono());
        resource.setActivo(usuario.getActivo());
        long activos = usuario.getPrestamos().stream()
                .filter(p -> p.getEstado() == Prestamo.EstadoPrestamo.ACTIVO)
                .count();
        resource.setPrestamosActivos((int) activos);
        return resource;
    }
 
    public void updateEntityFromDTO(UsuarioUpdateDTO dto, Usuario usuario) {
        if (dto.getNombre() != null) usuario.setNombre(dto.getNombre());
        if (dto.getApellido() != null) usuario.setApellido(dto.getApellido());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getTelefono() != null) usuario.setTelefono(dto.getTelefono());
        if (dto.getActivo() != null) usuario.setActivo(dto.getActivo());
    }
}