package com.bibliotecawisa.Service;

import com.bibliotecawisa.Exception.ConflictoException;
import com.bibliotecawisa.Exception.RecursoNoEncontradoException;
import com.bibliotecawisa.Model.DTO.UsuarioCreateDTO;
import com.bibliotecawisa.Model.DTO.UsuarioUpdateDTO;
import com.bibliotecawisa.Model.Entidad.Usuario;
import com.bibliotecawisa.Model.Mapper.UsuarioMapper;
import com.bibliotecawisa.Model.Resource.UsuarioResource;
import com.bibliotecawisa.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
public class UsuarioService {
 
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
 
    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }
 
    public List<UsuarioResource> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResource)
                .collect(Collectors.toList());
    }
 
    public UsuarioResource findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
        return usuarioMapper.toResource(usuario);
    }
 
    public UsuarioResource create(UsuarioCreateDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictoException("Ya existe un usuario con el email: " + dto.getEmail());
        }
        Usuario usuario = usuarioMapper.toEntity(dto);
        return usuarioMapper.toResource(usuarioRepository.save(usuario));
    }
 
    public UsuarioResource update(Long id, UsuarioCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictoException("Ya existe un usuario con el email: " + dto.getEmail());
        }
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        return usuarioMapper.toResource(usuarioRepository.save(usuario));
    }
 
    public UsuarioResource partialUpdate(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
        if (dto.getEmail() != null && !usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictoException("Ya existe un usuario con el email: " + dto.getEmail());
        }
        usuarioMapper.updateEntityFromDTO(dto, usuario);
        return usuarioMapper.toResource(usuarioRepository.save(usuario));
    }
 
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
 
    public List<UsuarioResource> findActivos() {
        return usuarioRepository.findByActivo(true).stream()
                .map(usuarioMapper::toResource)
                .collect(Collectors.toList());
    }
 
    public List<UsuarioResource> buscar(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre).stream()
                .map(usuarioMapper::toResource)
                .collect(Collectors.toList());
    }
}