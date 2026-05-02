package com.bibliotecawisa.Model.Entidad;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Column(name = "stock_total", nullable = false)
    private Integer stockTotal;

    @Column(name = "stock_disponible", nullable = false)
    private Integer stockDisponible;

    private String genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prestamo> prestamos = new ArrayList<>();

    public Libro() {}

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
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public List<Prestamo> getPrestamos() { return prestamos; }
    public void setPrestamos(List<Prestamo> prestamos) { this.prestamos = prestamos; }
}