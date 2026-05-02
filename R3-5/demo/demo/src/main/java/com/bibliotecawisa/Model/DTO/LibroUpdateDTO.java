package com.bibliotecawisa.Model.DTO;

import jakarta.validation.constraints.Positive;
 
public class LibroUpdateDTO {
 
    private String titulo;
    private Integer anioPublicacion;
 
    @Positive(message = "El stock total debe ser positivo")
    private Integer stockTotal;
 
    private String genero;
    private Long autorId;
 
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public Integer getStockTotal() { return stockTotal; }
    public void setStockTotal(Integer stockTotal) { this.stockTotal = stockTotal; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }
}
 