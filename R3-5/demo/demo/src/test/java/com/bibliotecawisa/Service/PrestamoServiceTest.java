package com.bibliotecawisa.Service;

import com.bibliotecawisa.Exception.RecursoNoEncontradoException;
import com.bibliotecawisa.Exception.ReglaNegocioException;
import com.bibliotecawisa.Model.DTO.PrestamoCreateDTO;
import com.bibliotecawisa.Model.Entidad.Libro;
import com.bibliotecawisa.Model.Entidad.Prestamo;
import com.bibliotecawisa.Model.Entidad.Prestamo.EstadoPrestamo;
import com.bibliotecawisa.Model.Entidad.Usuario;
import com.bibliotecawisa.Model.Mapper.PrestamoMapper;
import com.bibliotecawisa.Model.Resource.PrestamoResource;
import com.bibliotecawisa.Repository.LibroRepository;
import com.bibliotecawisa.Repository.PrestamoRepository;
import com.bibliotecawisa.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.time.LocalDate;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {
 
    @Mock
    private PrestamoRepository prestamoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private LibroRepository libroRepository;
    @Mock
    private PrestamoMapper prestamoMapper;
 
    @InjectMocks
    private PrestamoService prestamoService;
 
    private Usuario usuario;
    private Libro libro;
    private PrestamoCreateDTO prestamoCreateDTO;
    private Prestamo prestamo;
    private PrestamoResource prestamoResource;
 
    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Ana");
        usuario.setApellido("Martínez");
        usuario.setActivo(true);
 
        libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("Cien años de soledad");
        libro.setStockDisponible(3);
        libro.setStockTotal(5);
 
        prestamoCreateDTO = new PrestamoCreateDTO();
        prestamoCreateDTO.setUsuarioId(1L);
        prestamoCreateDTO.setLibroId(1L);
        prestamoCreateDTO.setFechaDevolucionPrevista(LocalDate.now().plusDays(15));
 
        prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setEstado(EstadoPrestamo.ACTIVO);
        prestamo.setFechaPrestamo(LocalDate.now());
 
        prestamoResource = new PrestamoResource();
        prestamoResource.setId(1L);
        prestamoResource.setEstado(EstadoPrestamo.ACTIVO);
    }
 
    @Test
    void create_cuandoTodoEsCorrecto_creaPrestamoYReduceStock() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(prestamoRepository.existsByUsuarioIdAndLibroIdAndEstado(1L, 1L, EstadoPrestamo.ACTIVO)).thenReturn(false);
        when(prestamoMapper.toEntity(any(), any(), any())).thenReturn(prestamo);
        when(prestamoRepository.save(any())).thenReturn(prestamo);
        when(prestamoMapper.toResource(prestamo)).thenReturn(prestamoResource);
 
        PrestamoResource resultado = prestamoService.create(prestamoCreateDTO);
 
        assertNotNull(resultado);
        assertEquals(2, libro.getStockDisponible());
        verify(libroRepository).save(libro);
    }
 
    @Test
    void create_cuandoUsuarioInactivo_lanzaReglaNegocio() {
        usuario.setActivo(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
 
        assertThrows(ReglaNegocioException.class, () -> prestamoService.create(prestamoCreateDTO));
        verify(prestamoRepository, never()).save(any());
    }
 
    @Test
    void create_cuandoSinStock_lanzaReglaNegocio() {
        libro.setStockDisponible(0);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
 
        assertThrows(ReglaNegocioException.class, () -> prestamoService.create(prestamoCreateDTO));
    }
 
    @Test
    void create_cuandoPrestamoYaActivo_lanzaReglaNegocio() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(prestamoRepository.existsByUsuarioIdAndLibroIdAndEstado(1L, 1L, EstadoPrestamo.ACTIVO)).thenReturn(true);
 
        assertThrows(ReglaNegocioException.class, () -> prestamoService.create(prestamoCreateDTO));
    }
 
    @Test
    void devolver_cuandoEstaActivo_devuelveYAumentaStock() {
        int stockInicial = libro.getStockDisponible();
        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));
        when(prestamoRepository.save(any())).thenReturn(prestamo);
        when(prestamoMapper.toResource(prestamo)).thenReturn(prestamoResource);
 
        prestamoService.devolver(1L);
 
        assertEquals(EstadoPrestamo.DEVUELTO, prestamo.getEstado());
        assertEquals(stockInicial + 1, libro.getStockDisponible());
    }
 
    @Test
    void devolver_cuandoYaDevuelto_lanzaReglaNegocio() {
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));
 
        assertThrows(ReglaNegocioException.class, () -> prestamoService.devolver(1L));
    }
 
    @Test
    void findById_cuandoNoExiste_lanzaExcepcion() {
        when(prestamoRepository.findById(99L)).thenReturn(Optional.empty());
 
        assertThrows(RecursoNoEncontradoException.class, () -> prestamoService.findById(99L));
    }
}
 