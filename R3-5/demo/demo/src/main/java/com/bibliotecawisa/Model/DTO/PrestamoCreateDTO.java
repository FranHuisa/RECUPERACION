package com.bibliotecawisa.Model.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
 
public class PrestamoCreateDTO {
 
    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;
 
    @NotNull(message = "El libro es obligatorio")
    private Long libroId;
 
    @NotNull(message = "La fecha de devolución prevista es obligatoria")
    @Future(message = "La fecha de devolución debe ser futura")
    private LocalDate fechaDevolucionPrevista;
 
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }
    public LocalDate getFechaDevolucionPrevista() { return fechaDevolucionPrevista; }
    public void setFechaDevolucionPrevista(LocalDate fechaDevolucionPrevista) { this.fechaDevolucionPrevista = fechaDevolucionPrevista; }
}
 