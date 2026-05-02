package com.bibliotecawisa.Controller;

import com.bibliotecawisa.Exception.GlobalExceptionHandler;
import com.bibliotecawisa.Exception.ReglaNegocioException;
import com.bibliotecawisa.Model.Entidad.Prestamo.EstadoPrestamo;
import com.bibliotecawisa.Model.Resource.PrestamoResource;
import com.bibliotecawisa.Service.PrestamoService;
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
 
import java.time.LocalDate;
import java.util.List;
 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(PrestamoController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class PrestamoControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private PrestamoService prestamoService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    private PrestamoResource prestamoResource;
 
    @BeforeEach
    void setUp() {
        prestamoResource = new PrestamoResource();
        prestamoResource.setId(1L);
        prestamoResource.setUsuarioId(1L);
        prestamoResource.setNombreUsuario("Ana Martínez");
        prestamoResource.setLibroId(1L);
        prestamoResource.setTituloLibro("Cien años de soledad");
        prestamoResource.setFechaPrestamo(LocalDate.now());
        prestamoResource.setFechaDevolucionPrevista(LocalDate.now().plusDays(15));
        prestamoResource.setEstado(EstadoPrestamo.ACTIVO);
    }
 
    @Test
    void getAll_retornaListaYStatus200() throws Exception {
        when(prestamoService.findAll()).thenReturn(List.of(prestamoResource));
 
        mockMvc.perform(get("/api/v1/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tituloLibro").value("Cien años de soledad"))
                .andExpect(jsonPath("$[0].estado").value("ACTIVO"));
    }
 
    @Test
    void create_conDatosValidos_retorna201() throws Exception {
        when(prestamoService.create(any())).thenReturn(prestamoResource);
 
        String body = """
                {
                    "usuarioId": 1,
                    "libroId": 1,
                    "fechaDevolucionPrevista": "%s"
                }
                """.formatted(LocalDate.now().plusDays(15));
 
        mockMvc.perform(post("/api/v1/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }
 
    @Test
    void create_sinStock_retorna400() throws Exception {
        when(prestamoService.create(any()))
                .thenThrow(new ReglaNegocioException("No hay ejemplares disponibles del libro: Cien años de soledad"));
 
        String body = """
                {
                    "usuarioId": 1,
                    "libroId": 1,
                    "fechaDevolucionPrevista": "%s"
                }
                """.formatted(LocalDate.now().plusDays(15));
 
        mockMvc.perform(post("/api/v1/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("No hay ejemplares disponibles del libro: Cien años de soledad"));
    }
 
    @Test
    void devolver_retornaPrestamoDevuelto() throws Exception {
        prestamoResource.setEstado(EstadoPrestamo.DEVUELTO);
        when(prestamoService.devolver(1L)).thenReturn(prestamoResource);
 
        mockMvc.perform(patch("/api/v1/prestamos/1/devolver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("DEVUELTO"));
    }
 
    @Test
    void getByEstado_retornaFiltrado() throws Exception {
        when(prestamoService.findByEstado(EstadoPrestamo.ACTIVO)).thenReturn(List.of(prestamoResource));
 
        mockMvc.perform(get("/api/v1/prestamos/estado/ACTIVO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("ACTIVO"));
    }
}