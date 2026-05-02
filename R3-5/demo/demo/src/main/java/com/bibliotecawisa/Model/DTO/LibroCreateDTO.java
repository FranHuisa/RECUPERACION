package com.bibliotecawisa.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
 
public class LibroCreateDTO {
 
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
 
    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;
 
    private Integer anioPublicacion;
 
    @NotNull(message = "El stock total es obligatorio")
    @Positive(message = "El stock total debe ser positivo")
    private Integer stockTotal;
 
    private String genero;
 
    @NotNull(message = "El autor es obligatorio")
    private Long autorId;
 
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public Integer getStockTotal() { return stockTotal; }
    public void setStockTotal(Integer stockTotal) { this.stockTotal = stockTotal; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }
}
 