INSERT INTO autores (nombre, apellido, nacionalidad, fecha_nacimiento) VALUES
('Gabriel', 'García Márquez', 'Colombiana', '1927-03-06'),
('Isabel', 'Allende', 'Chilena', '1942-08-02'),
('Jorge Luis', 'Borges', 'Argentina', '1899-08-24'),
('Haruki', 'Murakami', 'Japonesa', '1949-01-12');
 
INSERT INTO libros (titulo, isbn, anio_publicacion, stock_total, stock_disponible, genero, autor_id) VALUES
('Cien años de soledad', '978-84-376-0494-7', 1967, 5, 5, 'Novela', 1),
('El amor en los tiempos del cólera', '978-84-376-0495-4', 1985, 3, 3, 'Novela', 1),
('La casa de los espíritus', '978-84-01-90572-0', 1982, 4, 4, 'Novela', 2),
('Ficciones', '978-84-206-1262-0', 1944, 2, 2, 'Cuentos', 3),
('Norwegian Wood', '978-84-8310-137-4', 1987, 6, 6, 'Novela', 4),
('Kafka en la orilla', '978-84-8310-272-2', 2002, 3, 3, 'Novela', 4);
 
INSERT INTO usuarios (nombre, apellido, email, telefono, activo) VALUES
('Ana', 'Martínez', 'ana.martinez@email.com', '612345678', true),
('Carlos', 'López', 'carlos.lopez@email.com', '623456789', true),
('María', 'García', 'maria.garcia@email.com', '634567890', true),
('Pedro', 'Sánchez', 'pedro.sanchez@email.com', '645678901', true);

INSERT INTO prestamos (usuario_id,libro_id,fecha_prestamo,fecha_devolucion_prevista,fecha_devolucion_real,estado) VALUES
(1, 2, '2026-05-20', '2026-05-27', NULL, 'ACTIVO'),
(2, 1, '2026-05-18', '2026-05-25', '2026-05-22', 'DEVUELTO'),
(3, 3, '2026-05-19', '2026-05-26', NULL, 'ACTIVO');