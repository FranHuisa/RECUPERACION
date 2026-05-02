package com.bibliotecawisa.Controller;

import com.bibliotecawisa.Model.DTO.UsuarioCreateDTO;
import com.bibliotecawisa.Model.DTO.UsuarioUpdateDTO;
import com.bibliotecawisa.Model.Resource.UsuarioResource;
import com.bibliotecawisa.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
 
    private final UsuarioService usuarioService;
 
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
 
    @GetMapping
    public ResponseEntity<List<UsuarioResource>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResource> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }
 
    @PostMapping
    public ResponseEntity<UsuarioResource> create(@Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(dto));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResource> update(@PathVariable Long id, @Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(usuarioService.update(id, dto));
    }
 
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResource> partialUpdate(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.partialUpdate(id, dto));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
 
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioResource>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(usuarioService.buscar(nombre));
    }
 
    @GetMapping("/activos")
    public ResponseEntity<List<UsuarioResource>> getActivos() {
        return ResponseEntity.ok(usuarioService.findActivos());
    }
}