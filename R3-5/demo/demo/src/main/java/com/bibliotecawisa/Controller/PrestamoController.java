package com.bibliotecawisa.Controller;

import com.bibliotecawisa.Model.DTO.PrestamoCreateDTO;
import com.bibliotecawisa.Model.DTO.PrestamoUpdateDTO;
import com.bibliotecawisa.Model.Entidad.Prestamo.EstadoPrestamo;
import com.bibliotecawisa.Model.Resource.PrestamoResource;
import com.bibliotecawisa.Service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/v1/prestamos")
public class PrestamoController {
 
    private final PrestamoService prestamoService;
 
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }
 
    @GetMapping
    public ResponseEntity<List<PrestamoResource>> getAll() {
        return ResponseEntity.ok(prestamoService.findAll());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResource> getById(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.findById(id));
    }
 
    @PostMapping
    public ResponseEntity<PrestamoResource> create(@Valid @RequestBody PrestamoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.create(dto));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<PrestamoResource> update(@PathVariable Long id, @RequestBody PrestamoUpdateDTO dto) {
        return ResponseEntity.ok(prestamoService.update(id, dto));
    }
 
    @PatchMapping("/{id}")
    public ResponseEntity<PrestamoResource> partialUpdate(@PathVariable Long id, @RequestBody PrestamoUpdateDTO dto) {
        return ResponseEntity.ok(prestamoService.partialUpdate(id, dto));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prestamoService.delete(id);
        return ResponseEntity.noContent().build();
    }
 
    @PatchMapping("/{id}/devolver")
    public ResponseEntity<PrestamoResource> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolver(id));
    }
 
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PrestamoResource>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(prestamoService.findByUsuario(usuarioId));
    }
 
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PrestamoResource>> getByEstado(@PathVariable EstadoPrestamo estado) {
        return ResponseEntity.ok(prestamoService.findByEstado(estado));
    }
 
    @GetMapping("/vencidos")
    public ResponseEntity<List<PrestamoResource>> getVencidos() {
        return ResponseEntity.ok(prestamoService.findVencidos());
    }
}