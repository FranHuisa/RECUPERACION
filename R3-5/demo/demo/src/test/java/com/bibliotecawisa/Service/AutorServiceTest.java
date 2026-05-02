package com.bibliotecawisa.Service;

import com.bibliotecawisa.Exception.ConflictoException;
import com.bibliotecawisa.Exception.RecursoNoEncontradoException;
import com.bibliotecawisa.Model.DTO.AutorCreateDTO;
import com.bibliotecawisa.Model.Entidad.Autor;
import com.bibliotecawisa.Model.Mapper.AutorMapper;
import com.bibliotecawisa.Model.Resource.AutorResource;
import com.bibliotecawisa.Repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
class AutorServiceTest {
 
    @Mock
    private AutorRepository autorRepository;
 
    @Mock
    private AutorMapper autorMapper;
 
    @InjectMocks
    private AutorService autorService;
 
    private Autor autor;
    private AutorResource autorResource;
    private AutorCreateDTO autorCreateDTO;
 
    @BeforeEach
    void setUp() {
        autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Gabriel");
        autor.setApellido("García Márquez");
        autor.setNacionalidad("Colombiana");
 
        autorResource = new AutorResource();
        autorResource.setId(1L);
        autorResource.setNombre("Gabriel");
        autorResource.setApellido("García Márquez");
 
        autorCreateDTO = new AutorCreateDTO();
        autorCreateDTO.setNombre("Gabriel");
        autorCreateDTO.setApellido("García Márquez");
    }
 
    @Test
    void findById_cuandoExiste_retornaResource() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(autorMapper.toResource(autor)).thenReturn(autorResource);
 
        AutorResource resultado = autorService.findById(1L);
 
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(autorRepository).findById(1L);
    }
 
    @Test
    void findById_cuandoNoExiste_lanzaExcepcion() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());
 
        assertThrows(RecursoNoEncontradoException.class, () -> autorService.findById(99L));
    }
 
    @Test
    void create_cuandoNoExiste_guardaCorrectamente() {
        when(autorRepository.findByNombreAndApellido(any(), any())).thenReturn(Optional.empty());
        when(autorMapper.toEntity(autorCreateDTO)).thenReturn(autor);
        when(autorRepository.save(autor)).thenReturn(autor);
        when(autorMapper.toResource(autor)).thenReturn(autorResource);
 
        AutorResource resultado = autorService.create(autorCreateDTO);
 
        assertNotNull(resultado);
        verify(autorRepository).save(autor);
    }
 
    @Test
    void create_cuandoYaExiste_lanzaConflicto() {
        when(autorRepository.findByNombreAndApellido(any(), any())).thenReturn(Optional.of(autor));
 
        assertThrows(ConflictoException.class, () -> autorService.create(autorCreateDTO));
        verify(autorRepository, never()).save(any());
    }
 
    @Test
    void findAll_retornaListaDeResources() {
        when(autorRepository.findAll()).thenReturn(List.of(autor));
        when(autorMapper.toResource(autor)).thenReturn(autorResource);
 
        List<AutorResource> resultado = autorService.findAll();
 
        assertEquals(1, resultado.size());
        verify(autorRepository).findAll();
    }
}