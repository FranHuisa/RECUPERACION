# Sistema de Biblioteca - Wisa

## Descripción

Aplicación fullstack compuesta por un frontend SPA desarrollado con Angular 21 y una API REST desarrollada en Spring Boot.

El sistema permite gestionar una biblioteca con autenticación JWT, roles de usuario y operaciones CRUD completas.

---

## Tecnologías

- Angular 21
- TypeScript
- RxJS
- Spring Boot 3
- MySQL 8
- JWT Authentication
- Docker / Docker Compose

---

## Estructura del proyecto

```
enunciado3/
├── docker-compose.yml
├── .env
├── README.md
├── wisa-frontend/
│   ├── Dockerfile
│   └── nginx.conf
└── wisa-backend/
    ├── Dockerfile
    └── src/main/resources/
        ├── application.properties
        └── data.sql
```

---

## Autenticación

La aplicación utiliza JWT para autenticación.

- El token se almacena en `localStorage`
- Se envía automáticamente mediante `HttpInterceptor`
- Las rutas están protegidas con `AuthGuard` y `RoleGuard`

---

## Roles del sistema

- `ROLE_ADMIN` — acceso completo (crear, editar, eliminar)
- `ROLE_USER` — solo lectura

---

## Credenciales de prueba

| Rol   | Usuario | Contraseña | Email          |
|-------|---------|------------|----------------|
| Admin | admin   | admin123   | admin@wisa.com |
| User  | user    | user123    | user@wisa.com  |

---

## Variables de entorno

Las variables se configuran en el fichero `.env` en la raíz del proyecto.

| Variable             | Valor por defecto                         | Descripción                   |
|----------------------|-------------------------------------------|-------------------------------|
| MYSQL_ROOT_PASSWORD  | root                                      | Contraseña root de MySQL      |
| MYSQL_DATABASE       | wisadb                                    | Nombre de la base de datos    |
| MYSQL_USER           | wisa                                      | Usuario de la base de datos   |
| MYSQL_PASSWORD       | wisa1234                                  | Contraseña del usuario        |
| JWT_SECRET           | wisaSecretKey2026XyZ9876543210AbCdEfGhIj  | Clave para firmar los JWT     |
| JWT_EXPIRATION       | 86400000                                  | Expiración del token (ms)     |
| SERVER_PORT          | 8080                                      | Puerto del backend            |

---

## Ejecución del proyecto

### Con Docker (recomendado)

Requisitos: Docker y Docker Compose instalados.

```bash
cd enunciado3
docker-compose up --build
```

- Frontend: http://localhost:4200
- Backend API: http://localhost:8080
- MySQL: localhost:3307

Para detener los servicios:

```bash
docker-compose down
```

Para detener y eliminar los volúmenes (borra la base de datos):

```bash
docker-compose down -v
```

### Sin Docker

Requisitos: Node.js 20+, Java 21, Maven, MySQL 8 corriendo en local.

Backend:

```bash
cd wisa-backend
mvn spring-boot:run
```

Frontend:

```bash
cd wisa-frontend
npm install
ng serve
```

La aplicación estará disponible en http://localhost:4200.
