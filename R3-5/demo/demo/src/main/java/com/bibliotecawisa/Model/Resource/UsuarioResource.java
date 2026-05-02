package com.bibliotecawisa.Model.Resource;

public class UsuarioResource {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Boolean activo;
    private int prestamosActivos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public int getPrestamosActivos() { return prestamosActivos; }
    public void setPrestamosActivos(int prestamosActivos) { this.prestamosActivos = prestamosActivos; }
}
