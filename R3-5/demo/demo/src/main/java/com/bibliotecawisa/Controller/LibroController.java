package com.bibliotecawisa.Controller;

import com.bibliotecawisa.Model.DTO.LibroCreateDTO;
import com.bibliotecawisa.Model.DTO.LibroUpdateDTO;
import com.bibliotecawisa.Model.Resource.LibroResource;
import com.bibliotecawisa.Service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {
 
    private final LibroService libroService;
 
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }
 
    @GetMapping
    public ResponseEntity<List<LibroResource>> getAll() {
        return ResponseEntity.ok(libroService.findAll());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<LibroResource> getById(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.findById(id));
    }
 
    @PostMapping
    public ResponseEntity<LibroResource> create(@Valid @RequestBody LibroCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.create(dto));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<LibroResource> update(@PathVariable Long id, @Valid @RequestBody LibroCreateDTO dto) {
        return ResponseEntity.ok(libroService.update(id, dto));
    }
 
    @PatchMapping("/{id}")
    public ResponseEntity<LibroResource> partialUpdate(@PathVariable Long id, @Valid @RequestBody LibroUpdateDTO dto) {
        return ResponseEntity.ok(libroService.partialUpdate(id, dto));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        libroService.delete(id);
        return ResponseEntity.noContent().build();
    }
 
    @GetMapping("/buscar")
    public ResponseEntity<List<LibroResource>> buscar(@RequestParam String titulo) {
        return ResponseEntity.ok(libroService.buscar(titulo));
    }
 
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<LibroResource>> getByGenero(@PathVariable String genero) {
        return ResponseEntity.ok(libroService.findByGenero(genero));
    }
 
    @GetMapping("/disponibles")
    public ResponseEntity<List<LibroResource>> getDisponibles() {
        return ResponseEntity.ok(libroService.findDisponibles());
    }
}