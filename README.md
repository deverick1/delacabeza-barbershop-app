# De La Cabeza Peluquería – Sistema de Gestión

Sistema de gestión para peluquería/barbería desarrollado con Spring Boot y HTML/CSS/JS vanilla.

---

## Stack tecnológico

**Backend**
- Java 21
- Spring Boot 4.0.3
- Spring Security + JWT
- Spring Data JPA / Hibernate
- MySQL
- Lombok
- Maven

**Frontend**
- HTML5 / CSS3 / JavaScript vanilla
- Bootstrap 5.3
- Bootstrap Icons
- Google Fonts (Lexend Deca + Space Mono)

---

## Estructura del proyecto

```
barberiapp/                        ← Backend Spring Boot
├── src/main/java/com/analistas/barberiapp/
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── DataInitializer.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── ClienteController.java
│   │   ├── TurnoController.java
│   │   ├── ColaTurnoController.java
│   │   └── ServicioController.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── ClienteService.java
│   │   ├── TurnoService.java
│   │   ├── ColaTurnoService.java
│   │   └── ServicioService.java
│   ├── repository/
│   │   ├── UsuarioRepository.java
│   │   ├── ClienteRepository.java
│   │   ├── TurnoRepository.java
│   │   ├── ColaTurnoRepository.java
│   │   └── ServicioRepository.java
│   ├── model/
│   │   ├── Usuario.java
│   │   ├── Cliente.java
│   │   ├── Turno.java
│   │   ├── ColaTurno.java
│   │   └── Servicio.java
│   ├── dto/
│   ├── security/
│   │   ├── JwtUtil.java
│   │   ├── JwtFilter.java
│   │   └── CustomUserDetailService.java
│   └── exception/
│       └── GlobalExceptionHandler.java
└── src/main/resources/
    └── application.properties

barberiapp-frontend/               ← Frontend
├── index.html
├── css/
│   └── styles.css
└── js/
    ├── app.js
    ├── auth.js
    ├── dashboard.js
    ├── cola.js
    ├── turnos.js
    ├── clientes.js
    └── servicios.js
```

---

## Requisitos previos

- Java 21
- MySQL 8+
- Maven (incluido via mvnw)
- Navegador moderno (Chrome, Firefox, Safari)

---

## Funcionalidades

### Dashboard
- Resumen de turnos del día
- Servicios realizados con recaudación

### Orden de llegada
- Contadores por tipo de servicio (CORTE, BARBA, COMBO y personalizados)
- Total recaudado en tiempo real
- Agregar nuevos tipos de servicio

### Turnos
- Agendar turnos con cliente, fecha, hora y servicio
- Filtrar por fecha
- Cambiar estado (Pendiente → Completado / Cancelado)

### Clientes
- ABM completo de clientes
- Búsqueda por nombre

### Precios
- Actualización de precios por servicio desde la interfaz

---

## Endpoints principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/auth/login` | Login |
| GET | `/api/clientes` | Listar clientes |
| POST | `/api/clientes` | Crear cliente |
| PUT | `/api/clientes/{id}` | Editar cliente |
| DELETE | `/api/clientes/{id}` | Eliminar cliente |
| GET | `/api/turnos?fecha=` | Listar turnos por fecha |
| POST | `/api/turnos` | Crear turno |
| PATCH | `/api/turnos/{id}/estado` | Cambiar estado turno |
| DELETE | `/api/turnos/{id}` | Eliminar turno |
| GET | `/api/servicios` | Listar servicios y precios |
| PATCH | `/api/servicios/{id}/precio` | Actualizar precio |
| POST | `/api/servicios` | Crear nuevo tipo de servicio |

Todos los endpoints excepto `/api/auth/**` requieren header:
```
Authorization: Bearer <token>
```

---

## Autor

Proyecto universitario – Sistema de gestión para peluquería/barbería local.
