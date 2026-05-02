package com.bibliotecawisa.Controller;

import com.bibliotecawisa.Model.DTO.AutorCreateDTO;
import com.bibliotecawisa.Model.DTO.AutorUpdateDTO;
import com.bibliotecawisa.Model.Resource.AutorResource;
import com.bibliotecawisa.Service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/v1/autores")
public class AutorController {
 
    private final AutorService autorService;
 
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }
 
    @GetMapping
    public ResponseEntity<List<AutorResource>> getAll() {
        return ResponseEntity.ok(autorService.findAll());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<AutorResource> getById(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.findById(id));
    }
 
    @PostMapping
    public ResponseEntity<AutorResource> create(@Valid @RequestBody AutorCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.create(dto));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<AutorResource> update(@PathVariable Long id, @Valid @RequestBody AutorCreateDTO dto) {
        return ResponseEntity.ok(autorService.update(id, dto));
    }
 
    @PatchMapping("/{id}")
    public ResponseEntity<AutorResource> partialUpdate(@PathVariable Long id, @Valid @RequestBody AutorUpdateDTO dto) {
        return ResponseEntity.ok(autorService.partialUpdate(id, dto));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
 
    @GetMapping("/buscar")
    public ResponseEntity<List<AutorResource>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(autorService.buscar(nombre));
    }
 
    @GetMapping("/nacionalidad/{nacionalidad}")
    public ResponseEntity<List<AutorResource>> getByNacionalidad(@PathVariable String nacionalidad) {
        return ResponseEntity.ok(autorService.findByNacionalidad(nacionalidad));
    }
}