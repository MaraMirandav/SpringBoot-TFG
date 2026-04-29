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
 * le decimos explícitamente a Spring Data que escanee TODO el proyecto:
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
    // Sin beans adicionales — las anotaciones a nivel de clase son suficientes.
    // @EnableJpaRepositories genera internamente los proxies de cada JpaRepository.
    // @EntityScan registra todas las clases @Entity para que Hibernate las mapee.
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. @EnableJpaRepositories: activa Spring Data JPA para el paquete indicado.
//    Sin esto, solo se escanea el paquete del @SpringBootApplication.
// 2. @EntityScan: dice a Hibernate qué paquetes contienen clases @Entity.
//    También necesario cuando las entidades están fuera del paquete principal.
// 3. Por qué aquí y no en AppApplication: separación de responsabilidades.
//    AppApplication queda limpio; la config JPA vive en el módulo core.
// 4. Esto REEMPLAZA la auto-configuración de Spring Boot para JPA — Spring Boot
//    la retira cuando detecta un @EnableJpaRepositories explícito en el contexto.
// ─────────────────────────────────────────────────────────────────────────────
