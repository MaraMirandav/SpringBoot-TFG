package com.centros_sass.admin.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * TenantEntity — empresa cliente (tenant) de la plataforma SaasCon.
 *
 * Vive en schema "public" — es dato global del panel admin.
 * Mapea a la tabla creada en: V1__create_tenants_table.sql
 *
 * El "slug" es el identificador técnico clave de un tenant:
 *   - Nombre del schema PostgreSQL: tenant_{slug}
 *   - Claim "tenant_id" en el JWT de sus usuarios
 *   - Futuro: subdominio (acme.saascon.com)
 *
 * NUNCA exponer esta entidad directamente en API responses.
 * Usar TenantMapper para convertir a TenantResponse DTO.
 */
@Entity
@Table(name = "tenants", schema = "public")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantEntity {

    /**
     * PK autoincremental — generada por BIGSERIAL en PostgreSQL.
     * GenerationType.IDENTITY delega el autoincremento a la BD.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre visible de la empresa: "Acme Corp", "Globex Industries"
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Identificador URL-safe único: solo minúsculas, números y guiones.
     * Regex en el DTO: ^[a-z0-9-]+$
     * Define el nombre del schema PostgreSQL: tenant_{slug}
     * unique = true en @Column + UNIQUE en la migración SQL → doble garantía.
     */
    @Column(name = "slug", nullable = false, unique = true, length = 50)
    private String slug;

    // Email del administrador principal del tenant
    @Column(name = "admin_email", nullable = false, length = 255)
    private String adminEmail;

    /**
     * Estado del tenant en el ciclo de vida SaaS.
     * EnumType.STRING → guarda "ACTIVE" en BD, no 0 (ordinal).
     * EnumType.ORDINAL es peligroso: si reordenas el enum, los datos se corrompen.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TenantStatus status;

    /**
     * Timestamp de creación — Spring Data lo asigna automáticamente al primer save().
     * updatable = false: la fecha de creación nunca debe cambiar.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Timestamp de última modificación — actualizado por Spring Data en cada save().
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. schema = "public" OBLIGATORIO: sin esto Hibernate usaría el tenant activo
//    del ThreadLocal — los tenants son datos globales, no por-tenant
// 2. @Enumerated(EnumType.STRING): siempre STRING, nunca ORDINAL
//    Si reordenas los valores del enum con ORDINAL, los datos en BD cambian de significado
// 3. @CreatedDate + @LastModifiedDate: Spring Data audita timestamps automáticamente
//    Requiere @EnableJpaAuditing en algún @Configuration (se agrega en un paso futuro)
// 4. unique = true en slug: documenta el invariante en el código Java, no solo en SQL
//    Hibernate también crea una constraint si se usa schema generation
// 5. @Builder + @NoArgsConstructor + @AllArgsConstructor: los tres juntos son necesarios
//    @Builder necesita @AllArgsConstructor; JPA necesita @NoArgsConstructor sin argumentos
// ─────────────────────────────────────────────────────────────────────────────
