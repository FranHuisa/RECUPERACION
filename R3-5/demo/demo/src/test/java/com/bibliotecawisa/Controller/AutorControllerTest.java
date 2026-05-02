package com.bibliotecawisa.Controller;

import com.bibliotecawisa.Exception.GlobalExceptionHandler;
import com.bibliotecawisa.Exception.RecursoNoEncontradoException;
import com.bibliotecawisa.Model.Resource.AutorResource;
import com.bibliotecawisa.Service.AutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import java.util.List;
 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(AutorController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class AutorControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private AutorService autorService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    private AutorResource autorResource;
 
    @BeforeEach
    void setUp() {
        autorResource = new AutorResource();
        autorResource.setId(1L);
        autorResource.setNombre("Gabriel");
        autorResource.setApellido("García Márquez");
        autorResource.setNacionalidad("Colombiana");
        autorResource.setTotalLibros(2);
    }
 
    @Test
    void getAll_retornaListaYStatus200() throws Exception {
        when(autorService.findAll()).thenReturn(List.of(autorResource));
 
        mockMvc.perform(get("/api/v1/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Gabriel"))
                .andExpect(jsonPath("$[0].apellido").value("García Márquez"));
    }
 
    @Test
    void getById_cuandoExiste_retornaResourceYStatus200() throws Exception {
        when(autorService.findById(1L)).thenReturn(autorResource);
 
        mockMvc.perform(get("/api/v1/autores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Gabriel"));
    }
 
    @Test
    void getById_cuandoNoExiste_retorna404() throws Exception {
        when(autorService.findById(99L)).thenThrow(new RecursoNoEncontradoException("Autor no encontrado con id: 99"));
 
        mockMvc.perform(get("/api/v1/autores/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Autor no encontrado con id: 99"));
    }
 
    @Test
    void create_conDatosValidos_retorna201() throws Exception {
        when(autorService.create(any())).thenReturn(autorResource);
 
        String body = """
                {
                    "nombre": "Gabriel",
                    "apellido": "García Márquez",
                    "nacionalidad": "Colombiana"
                }
                """;
 
        mockMvc.perform(post("/api/v1/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Gabriel"));
    }
 
    @Test
    void create_sinNombre_retorna400() throws Exception {
        String body = """
                {
                    "apellido": "García Márquez"
                }
                """;
 
        mockMvc.perform(post("/api/v1/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.nombre").value("El nombre es obligatorio"));
    }
}