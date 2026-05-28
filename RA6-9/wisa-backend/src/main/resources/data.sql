INSERT IGNORE INTO roles (name) VALUES ('ROLE_USER');
INSERT IGNORE INTO roles (name) VALUES ('ROLE_ADMIN');

-- admin / admin123
INSERT IGNORE INTO users (username, email, password) VALUES (
    'admin',
    'admin@wisa.com',
    '$2a$10$rXL9hOWwDVnigVKraHX5bu/T1fZBW5gvSytyEJldLmPZvhQTsGjgq'
);
UPDATE users
SET password = '$2a$10$rXL9hOWwDVnigVKraHX5bu/T1fZBW5gvSytyEJldLmPZvhQTsGjgq'
WHERE username = 'admin';

-- user / user123
INSERT IGNORE INTO users (username, email, password) VALUES (
    'user',
    'user@wisa.com',
    '$2a$10$zAYMtabfyCxxGVx4y6tPGOK0e9Fy5eaPJ8.hYFzxvwV7BAqS6Xw.S'
);
UPDATE users
SET password = '$2a$10$zAYMtabfyCxxGVx4y6tPGOK0e9Fy5eaPJ8.hYFzxvwV7BAqS6Xw.S'
WHERE username = 'user';

INSERT IGNORE INTO user_roles (user_id, role_id)
    SELECT u.id, r.id FROM users u, roles r
    WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

INSERT IGNORE INTO user_roles (user_id, role_id)
    SELECT u.id, r.id FROM users u, roles r
    WHERE u.username = 'user' AND r.name = 'ROLE_USER';

INSERT IGNORE INTO categories (name, description) VALUES
('Ficción', 'Novelas y cuentos de ficción literaria'),
('Ciencia', 'Libros de divulgación científica'),
('Historia', 'Libros de historia universal y local'),
('Tecnología', 'Libros sobre programación y tecnología');

INSERT IGNORE INTO products (name, description, price, stock, category_id) VALUES
('El Quijote', 'La obra cumbre de la literatura española', 12.99, 15, (SELECT id FROM categories WHERE name='Ficción')),
('Cien años de soledad', 'Obra maestra del realismo mágico', 14.50, 10, (SELECT id FROM categories WHERE name='Ficción')),
('Breve historia del tiempo', 'Stephen Hawking explica el universo', 11.99, 8, (SELECT id FROM categories WHERE name='Ciencia')),
('Sapiens', 'Historia breve de la humanidad', 16.99, 12, (SELECT id FROM categories WHERE name='Historia')),
('Clean Code', 'Guía para escribir código limpio', 29.99, 5, (SELECT id FROM categories WHERE name='Tecnología'));
