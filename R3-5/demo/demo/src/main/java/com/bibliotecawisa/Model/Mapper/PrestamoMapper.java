package com.bibliotecawisa.Model.Mapper;

import com.bibliotecawisa.Model.DTO.PrestamoUpdateDTO;
import com.bibliotecawisa.Model.Entidad.Libro;
import com.bibliotecawisa.Model.Entidad.Prestamo;
import com.bibliotecawisa.Model.Entidad.Usuario;
import com.bibliotecawisa.Model.Resource.PrestamoResource;
import org.springframework.stereotype.Component;
 
import java.time.LocalDate;
 
@Component
public class PrestamoMapper {
 
    public Prestamo toEntity(Usuario usuario, Libro libro, LocalDate fechaDevolucionPrevista) {
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionPrevista(fechaDevolucionPrevista);
        prestamo.setEstado(Prestamo.EstadoPrestamo.ACTIVO);
        return prestamo;
    }
 
    public PrestamoResource toResource(Prestamo prestamo) {
        PrestamoResource resource = new PrestamoResource();
        resource.setId(prestamo.getId());
        resource.setUsuarioId(prestamo.getUsuario().getId());
        resource.setNombreUsuario(prestamo.getUsuario().getNombre() + " " + prestamo.getUsuario().getApellido());
        resource.setLibroId(prestamo.getLibro().getId());
        resource.setTituloLibro(prestamo.getLibro().getTitulo());
        resource.setFechaPrestamo(prestamo.getFechaPrestamo());
        resource.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista());
        resource.setFechaDevolucionReal(prestamo.getFechaDevolucionReal());
        resource.setEstado(prestamo.getEstado());
        return resource;
    }
 
    public void updateEntityFromDTO(PrestamoUpdateDTO dto, Prestamo prestamo) {
        if (dto.getFechaDevolucionPrevista() != null) prestamo.setFechaDevolucionPrevista(dto.getFechaDevolucionPrevista());
        if (dto.getFechaDevolucionReal() != null) prestamo.setFechaDevolucionReal(dto.getFechaDevolucionReal());
        if (dto.getEstado() != null) prestamo.setEstado(dto.getEstado());
    }
}