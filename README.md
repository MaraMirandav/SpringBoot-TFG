# TFG: Centros SaaS — Gestión Integral de Centros de Día

![Java 21](https://img.shields.io/badge/Java-21-%23ED8B00?logo=openjdk)
![Spring Boot 4.0](https://img.shields.io/badge/Spring%20Boot-4.0.6-%236DB33F?logo=springboot)
![PostgreSQL 18](https://img.shields.io/badge/PostgreSQL-18-%234169E1?logo=postgresql)
![JWT](https://img.shields.io/badge/Auth-JWT-000000?logo=jsonwebtokens)
![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red)

## Descripción

**Centros SaaS** es una aplicación backend SaaS diseñada para la administración integral de **centros de día para adultos mayores**. El proyecto surge de un diálogo con fundadoras de un centro de día que identificaron la falta de software adaptado a sus necesidades — las soluciones del mercado están pensadas para hospitales o residencias 24/7, generando interfaces sobrecargadas y flujos de trabajo frustrantes.

Esta API RESTful backend gestiona desde el control de medicación y transporte hasta la trazabilidad de higiene, incidencias y pertenencias de los usuarios, todo sobre una arquitectura multi-tenant preparada para escalar a múltiples centros independientes.

> **Frontend:** [Enlace al repositorio del frontend](https://github.com/JimmyRampage/Angular-TFG)

---

## Documentación del TFG

La memoria completa del Trabajo de Fin de Grado está disponible en formato PDF e incluye:

- Análisis de requisitos funcionales y no funcionales
- Diagramas de flujo y casos de uso
- Modelo de datos relacional
- Decisiones arquitectónicas

📄 [`docs/TFG-Saas_Gestion_Centros_de_Días.pdf`](docs/TFG-Saas_Gestion_Centros_de_Días.pdf)

---

## Stack Tecnológico

| Tecnología | Versión |
|------------|---------|
| **Lenguaje** | Java 21 |
| **Framework** | Spring Boot 4.0.6 |
| **Gestor de dependencias** | Maven |
| **Base de datos** | PostgreSQL 18 |
| **ORM** | Spring Data JPA (Hibernate) |
| **Seguridad** | Spring Security + JWT (JJWT 0.13.x) |
| **Mapeo DTO/Entity** | MapStruct 1.6.3 |
| **Construcción** | Lombok 1.18.44 |
| **Documentación API** | SpringDoc OpenAPI (Swagger UI) |
| **Contenedorización** | Docker (solo base de datos) |

---

## Arquitectura

El proyecto sigue una **arquitectura MVC** multicapa con los siguientes principios:

```
Cliente (HTTP)
    ↕
Controller (REST)      → @PreAuthorize (RBAC)
    ↕
Service (Interface + Impl)   → @Transactional
    ↕
Repository (JPA)        → Query Methods + JPQL
    ↕
Entity (JPA)            → PostgreSQL
```

- **Multi-tenant**: Arquitectura SaaS preparada para aislamiento de datos por centro.
- **Auditoría**: Trazabilidad completa de creación y modificación (`createdBy`, `updatedBy`, `createdAt`, `updatedAt`) mediante Spring Data JPA Auditing.
- **Seguridad**: Autenticación stateless JWT con 11 roles del sistema (Director, Enfermero, TAS, Conductor, etc.).
- **Manejo de errores**: Centralizado con `@ControllerAdvice` y respuestas JSON estandarizadas.

---

## Estructura del Proyecto

```
src/main/java/com/centros_sass/app/
├── config/           # Configuraciones (Security, JPA Auditing)
├── controller/       # REST Controllers por dominio
├── dto/              # Data Transfer Objects (Request/Response/Update)
├── exception/        # Manejo global de excepciones
├── generic/          # Clases genéricas (ApiDataResponse)
├── mapper/           # Interfaces MapStruct Entity ↔ DTO
├── model/            # Entidades JPA
├── repository/       # Repositorios Spring Data JPA
├── security/         # JWT, filtros, auditoría
├── service/          # Interfaces de servicio
│   └── impl/         # Implementaciones
└── utils/            # Utilidades (MapperHelper)
```

El proyecto cuenta con **51 entidades JPA**, **44 controladores REST**, y más de **100 servicios** organizados por dominio funcional.

---

## Cómo Empezar

### Prerrequisitos

- Java 21+
- Docker y Docker Compose
- Maven (o usar `./mvnw` incluido)

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/MaraMirandav/SpringBoot-TFG
cd TFG

# 2. Configurar variables de entorno
cp .env.example .env

# 3. Levantar PostgreSQL
docker-compose up -d

# 4. Ejecutar la aplicación
./mvnw spring-boot:run
```

La aplicación arrancará en `http://localhost:8080`.

### Scripts SQL

Los scripts de inicialización se encuentran en `src/main/resources/bbdd/`:

| Archivo | Descripción |
|---------|-------------|
| `schema.sql` | Crea el schema `schema_template` |
| `inserts.sql` | Datos de prueba (se ejecuta automáticamente) |
| `insert_admin.sql` | Roles + administrador inicial (manual) |

---

## Endpoints de la API

### Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/register` | Registro de trabajador |
| POST | `/api/auth/login` | Inicio de sesión (devuelve JWT) |

### Gestión de Personal

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET/POST | `/api/v1/workers` | CRUD trabajadores |
| GET/PUT/DELETE | `/api/v1/workers/{id}` | CRUD trabajador individual |
| GET/POST | `/api/v1/worker-schedules` | Horarios de trabajadores |
| GET/POST | `/api/v1/worker-schedule-records` | Registro de fichaje (entrada/salida) |

### Usuarios (Adultos Mayores)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET/POST | `/api/v1/users` | CRUD usuarios |
| GET/PUT/DELETE | `/api/v1/users/{id}` | CRUD usuario individual |
| GET/POST | `/api/v1/user-addresses` | Direcciones de usuarios |
| GET/POST | `/api/v1/user-contacts` | Contactos de emergencia |
| GET/POST | `/api/v1/user-attendance-days` | Asistencia diaria |
| GET/POST | `/api/v1/user-attendance-records` | Registros de asistencia |

### Medicación y Tratamientos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET/POST | `/api/v1/medications` | Inventario de medicamentos |
| GET/POST | `/api/v1/user-medical-infos` | Fichas médicas |
| GET/POST | `/api/v1/user-allergies` | Alergias de usuarios |
| GET/POST | `/api/v1/user-illnesses` | Enfermedades de usuarios |
| GET/POST | `/api/v1/treatment-details` | Tratamientos activos |

### Transporte

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET/POST | `/api/v1/transport-routes` | Rutas de transporte |
| GET/POST | `/api/v1/route-vehicles` | Vehículos |
| GET/POST | `/api/v1/transport-routes/{id}/passengers` | Pasajeros por ruta |

### Incidencias

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET/POST | `/api/v1/center-incidents` | Incidencias del centro |
| GET/POST | `/api/v1/user-incidents` | Incidencias de usuarios |
| GET/POST | `/api/v1/center-incidents/{id}/comments` | Comentarios de incidencias |
| GET/POST | `/api/v1/user-incidents/{id}/comments` | Comentarios de incidencias |

### Higiene y Pertenencias

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET/POST | `/api/v1/bathroom-schedules` | Registro de higiene |
| GET/POST | `/api/v1/user-belongings` | Pertenencias de usuarios |
| GET/POST | `/api/v1/user-clothings` | Ropa de usuarios |
| GET/POST | `/api/v1/user-diapers` | Pañales de usuarios |
| GET/POST | `/api/v1/user-objects` | Objetos personales |

### Catálogos

El sistema cuenta con **27 catálogos** (fijos y dinámicos) para gestionar tipologías como tipos de alergias, medicamentos, condiciones de objetos, turnos, etc. Todos bajo `/api/v1/catalogs/...`.

---

## Documentación Interactiva

### Swagger UI

Una vez iniciada la aplicación, puedes acceder a la documentación interactiva de la API en:

```
http://localhost:8080/swagger-ui/index.html
```

### Colección Postman

La colección completa de Postman con todos los endpoints documentados y ejemplos de uso se encuentra en:

📄 [`docs/TFG.postman_collection.json`](docs/TFG.postman_collection.json)

---

## Estado del Proyecto

| Fase | Estado |
|------|--------|
| **Fase 1** — Infraestructura Base (Auditoría, Seguridad JWT, Auth) | ✅ Completada |
| **Fase 2** — Gestión de Personal (Workers, Horarios, Fichaje) | ✅ Completada |
| **Fase 3** — Usuarios/Pacientes (CRUD, Contactos, Direcciones, Asistencia) | ✅ Completada |
| **Fase 4** — Core del Negocio (Medicación, Transporte, Incidencias, Higiene, Pertenencias) | ✅ Completada |
| **Fase 5** — Super Admin Multi-tenant | ✅ Completada |
| **Fase 6** — feature Planificación y Calendario | ❌ Pendiente |
| **Fase 7** — feature Comunicaciones y Notificaciones | ❌ Pendiente |

---

## Contribuir

Este proyecto es un Trabajo de Fin de Grado. Si deseas contribuir o tienes sugerencias, consulta el archivo [`AGENTS.md`](AGENTS.md) para conocer las convenciones de código, el flujo de trabajo y los skills disponibles para el desarrollo asistido.

---

## Licencia

Todos los derechos reservados.
