# WisaFrontend - Sistema de Biblioteca

##  Descripción
Aplicación SPA desarrollada con Angular 21 que consume una API REST desarrollada en Spring Boot.

El sistema permite gestionar una biblioteca con autenticación JWT, roles de usuario y operaciones CRUD completas.

---

##  Tecnologías
- Angular 21
- TypeScript
- RxJS
- Spring Boot (API backend)
- JWT Authentication
- Docker

---

##  Autenticación
La aplicación utiliza JWT para autenticación.

- El token se almacena en `localStorage`
- Se envía automáticamente mediante `HttpInterceptor`
- Las rutas están protegidas con `AuthGuard` y `RoleGuard`

---

##  Roles del sistema
- ADMIN → acceso completo (crear, editar, eliminar)
- USER → solo lectura

---

##  Ejecución del proyecto

### ▶ Sin Docker

Backend:
```bash
mvn spring-boot:run