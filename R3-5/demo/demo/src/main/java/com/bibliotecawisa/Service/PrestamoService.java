package com.bibliotecawisa.Service;

import com.bibliotecawisa.Exception.RecursoNoEncontradoException;
import com.bibliotecawisa.Exception.ReglaNegocioException;
import com.bibliotecawisa.Model.DTO.PrestamoCreateDTO;
import com.bibliotecawisa.Model.DTO.PrestamoUpdateDTO;
import com.bibliotecawisa.Model.Entidad.Libro;
import com.bibliotecawisa.Model.Entidad.Prestamo;
import com.bibliotecawisa.Model.Entidad.Prestamo.EstadoPrestamo;
import com.bibliotecawisa.Model.Entidad.Usuario;
import com.bibliotecawisa.Model.Mapper.PrestamoMapper;
import com.bibliotecawisa.Model.Resource.PrestamoResource;
import com.bibliotecawisa.Repository.LibroRepository;
import com.bibliotecawisa.Repository.PrestamoRepository;
import com.bibliotecawisa.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final PrestamoMapper prestamoMapper;

    public PrestamoService(PrestamoRepository prestamoRepository, UsuarioRepository usuarioRepository,
            LibroRepository libroRepository, PrestamoMapper prestamoMapper) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.prestamoMapper = prestamoMapper;
    }

    public List<PrestamoResource> findAll() {
        return prestamoRepository.findAll().stream()
                .map(prestamoMapper::toResource)
                .collect(Collectors.toList());
    }

    public PrestamoResource findById(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado con id: " + id));
        return prestamoMapper.toResource(prestamo);
    }

    public PrestamoResource create(PrestamoCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(
                        () -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + dto.getUsuarioId()));

        // Regla 1: el usuario debe estar activo
        if (!usuario.getActivo()) {
            throw new ReglaNegocioException("El usuario no está activo y no puede realizar préstamos");
        }

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id: " + dto.getLibroId()));

        // Regla 2: el libro debe tener stock disponible
        if (libro.getStockDisponible() <= 0) {
            throw new ReglaNegocioException("No hay ejemplares disponibles del libro: " + libro.getTitulo());
        }

        // Regla 3: el usuario no puede tener ya ese libro en prestamo activo
        if (prestamoRepository.existsByUsuarioIdAndLibroIdAndEstado(dto.getUsuarioId(), dto.getLibroId(),
                EstadoPrestamo.ACTIVO)) {
            throw new ReglaNegocioException("El usuario ya tiene ese libro en préstamo activo");
        }

        libro.setStockDisponible(libro.getStockDisponible() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = prestamoMapper.toEntity(usuario, libro, dto.getFechaDevolucionPrevista());
        return prestamoMapper.toResource(prestamoRepository.save(prestamo));
    }

    public PrestamoResource update(Long id, PrestamoUpdateDTO dto) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado con id: " + id));
        prestamoMapper.updateEntityFromDTO(dto, prestamo);
        return prestamoMapper.toResource(prestamoRepository.save(prestamo));
    }

    public PrestamoResource partialUpdate(Long id, PrestamoUpdateDTO dto) {
        return update(id, dto);
    }

    public PrestamoResource devolver(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado con id: " + id));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new ReglaNegocioException("El préstamo ya fue devuelto");
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        prestamo.setFechaDevolucionReal(LocalDate.now());

        Libro libro = prestamo.getLibro();
        libro.setStockDisponible(libro.getStockDisponible() + 1);
        libroRepository.save(libro);

        return prestamoMapper.toResource(prestamoRepository.save(prestamo));
    }

    public void delete(Long id) {
        if (!prestamoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Préstamo no encontrado con id: " + id);
        }
        prestamoRepository.deleteById(id);
    }

    public List<PrestamoResource> findByUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId).stream()
                .map(prestamoMapper::toResource)
                .collect(Collectors.toList());
    }

    public List<PrestamoResource> findByEstado(EstadoPrestamo estado) {
        return prestamoRepository.findByEstado(estado).stream()
                .map(prestamoMapper::toResource)
                .collect(Collectors.toList());
    }

    public List<PrestamoResource> findVencidos() {
        return prestamoRepository.findByFechaDevolucionPrevistaBefore(LocalDate.now()).stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.ACTIVO)
                .map(prestamoMapper::toResource)
                .collect(Collectors.toList());
    }
}