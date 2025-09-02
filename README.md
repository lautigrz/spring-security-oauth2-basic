# 🌐 OAuth2 Authentication + JWT Authorization - Spring Boot

Implementación básica de **autenticación y autorización** en Spring Boot usando **OAuth2 (GitHub)** y **JWT**. Este proyecto demuestra cómo proteger endpoints según roles y generar tokens JWT tras login exitoso.

---

## 🚀 Funcionalidades

- **Autenticación con GitHub OAuth2**  
  - Login con cuenta GitHub.  
  - Obtiene `username` y `email`.  
  - Guarda usuarios nuevos en la base de datos.

- **Autorización con JWT**  
  - Generación de token JWT tras login.  
  - Envío de token en `Authorization: Bearer <token>` para acceder a rutas protegidas.

- **Roles y control de acceso**  
  - Endpoints protegidos con `@PreAuthorize`.  
  - Roles: `USER` y `ADMIN`.

- **Filtro de seguridad JWT**  
  - `JwtAuthorizationFilter` valida el token en cada request y configura el contexto de seguridad.

---

## 🛠 Tecnologías

- Spring Boot 3.x  
- Spring Security (OAuth2 + JWT)  
- Spring Data JPA  
- H2 / MySQL (usuarios y roles)  
- GitHub OAuth2  


