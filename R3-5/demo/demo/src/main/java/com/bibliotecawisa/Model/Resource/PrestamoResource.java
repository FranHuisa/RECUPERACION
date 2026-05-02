package com.bibliotecawisa.Model.Resource;

import com.bibliotecawisa.Model.Entidad.Prestamo.EstadoPrestamo;
import java.time.LocalDate;

public class PrestamoResource {

    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private Long libroId;
    private String tituloLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;
    private EstadoPrestamo estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaDevolucionPrevista() { return fechaDevolucionPrevista; }
    public void setFechaDevolucionPrevista(LocalDate fechaDevolucionPrevista) { this.fechaDevolucionPrevista = fechaDevolucionPrevista; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; }
    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = estado; }
}
