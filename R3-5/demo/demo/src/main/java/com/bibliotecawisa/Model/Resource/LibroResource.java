package com.bibliotecawisa.Model.Resource;

public class LibroResource {

    private Long id;
    private String titulo;
    private String isbn;
    private Integer anioPublicacion;
    private Integer stockTotal;
    private Integer stockDisponible;
    private String genero;
    private Long autorId;
    private String nombreAutor;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public Integer getStockTotal() { return stockTotal; }
    public void setStockTotal(Integer stockTotal) { this.stockTotal = stockTotal; }
    public Integer getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(Integer stockDisponible) { this.stockDisponible = stockDisponible; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }
    public String getNombreAutor() { return nombreAutor; }
    public void setNombreAutor(String nombreAutor) { this.nombreAutor = nombreAutor; }
}
