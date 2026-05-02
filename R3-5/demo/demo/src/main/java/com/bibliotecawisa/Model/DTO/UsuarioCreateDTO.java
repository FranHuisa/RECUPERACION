package com.bibliotecawisa.Model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
 
public class UsuarioCreateDTO {
 
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
 
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
 
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;
 
    private String telefono;
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
 