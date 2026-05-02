package com.bibliotecawisa.Model.DTO;

import com.bibliotecawisa.Model.Entidad.Prestamo.EstadoPrestamo;
import java.time.LocalDate;
 
public class PrestamoUpdateDTO {
 
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;
    private EstadoPrestamo estado;
 
    public LocalDate getFechaDevolucionPrevista() { return fechaDevolucionPrevista; }
    public void setFechaDevolucionPrevista(LocalDate fechaDevolucionPrevista) { this.fechaDevolucionPrevista = fechaDevolucionPrevista; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; }
    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = estado; }
}
 