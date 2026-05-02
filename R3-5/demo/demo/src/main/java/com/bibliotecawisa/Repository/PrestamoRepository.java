package com.bibliotecawisa.Repository;

import com.bibliotecawisa.Model.Entidad.Prestamo;
import com.bibliotecawisa.Model.Entidad.Prestamo.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.time.LocalDate;
import java.util.List;
 
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
 
    List<Prestamo> findByUsuarioId(Long usuarioId);
 
    List<Prestamo> findByLibroId(Long libroId);
 
    List<Prestamo> findByEstado(EstadoPrestamo estado);
 
    boolean existsByUsuarioIdAndLibroIdAndEstado(Long usuarioId, Long libroId, EstadoPrestamo estado);
 
    List<Prestamo> findByFechaDevolucionPrevistaBefore(LocalDate fecha);
}
 