package com.centros_sass.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JpaConfig — amplía el escaneo de JPA a todos los módulos de com.centros_sass.
 *
 * PROBLEMA QUE RESUELVE:
 * Por defecto, Spring Boot escanea repositorios JPA y entidades @Entity solo
 * desde el paquete de @SpringBootApplication (com.centros_sass.app).
 * Los repositorios del módulo core (ej. AuditLogRepository en core.audit)
 * quedan fuera de ese alcance y Spring no los registra como beans.
 *
 * SOLUCIÓN:
 * Al declarar @EnableJpaRepositories y @EntityScan con basePackages = "com.centros_sass",
 * le decimos explícitamente a Spring Data que escanee ALL el proyecto:
 *   - com.centros_sass.app.repository.*    ← repositorios del módulo app
 *   - com.centros_sass.core.audit.*        ← AuditLogRepository
 *   - cualquier repositorio futuro en core.* o customer.*
 *
 * NOTA: Esta clase se detecta automáticamente porque AppApplication
 * tiene scanBasePackages = "com.centros_sass" → @ComponentScan la encuentra.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.centros_sass")
public class JpaConfig {
}
