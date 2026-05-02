package com.bibliotecawisa.Model.Resource;

import java.time.LocalDate;

public class AutorResource {

    private Long id;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private int totalLibros;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public int getTotalLibros() { return totalLibros; }
    public void setTotalLibros(int totalLibros) { this.totalLibros = totalLibros; }
}
