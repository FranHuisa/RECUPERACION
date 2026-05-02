package com.bibliotecawisa.Model.DTO;

import jakarta.validation.constraints.Email;
 
public class UsuarioUpdateDTO {
 
    private String nombre;
    private String apellido;
 
    @Email(message = "El email no tiene un formato válido")
    private String email;
 
    private String telefono;
    private Boolean activo;
 
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
}
 