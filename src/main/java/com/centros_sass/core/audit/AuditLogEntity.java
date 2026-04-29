package com.centros_sass.core.audit;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * AuditLogEntity — registra cada operación CREATE, UPDATE, DELETE del sistema.
 *
 * Vive en schema "public" para que el panel admin pueda hacer queries
 * cross-tenant (ver actividad de todos los tenants desde un solo lugar).
 *
 * INMUTABLE por diseño: una vez creado, un registro de auditoría NUNCA
 * se modifica. Si necesitas corregir, agregas una nueva fila.
 * Esto garantiza integridad del historial.
 *
 * Mapea a la tabla creada en: V3__create_audit_log_table.sql
 */
@Entity
@Table(name = "audit_log", schema = "public")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identificador del tenant que realizó la acción.
     * VARCHAR y no FK a tenants.slug: conservar historial si el tenant es eliminado.
     */
    @Column(name = "tenant_id", nullable = false, length = 100)
    private String tenantId;

    /**
     * Subject del JWT del usuario que ejecutó la acción.
     * Nullable: operaciones de sistema (Flyway, schedulers) no tienen usuario.
     */
    @Column(name = "user_id", length = 255)
    private String userId;

    // Tipo de entidad afectada: "tenant", "plan", "user", "invoice", etc.
    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType;

    // ID de la entidad — VARCHAR soporta tanto Long como UUID
    @Column(name = "entity_id", nullable = false, length = 255)
    private String entityId;

    // Operación: "CREATE", "UPDATE", "DELETE", "LOGIN", etc.
    @Column(name = "operation", nullable = false, length = 20)
    private String operation;

    /**
     * Timestamp de la operación.
     * @CreatedDate lo puebla Spring Data automáticamente al persistir.
     * updatable = false: un registro de auditoría no cambia su timestamp nunca.
     * Nota: requiere @EnableJpaAuditing en un @Configuration para activarse.
     */
    @CreatedDate
    @Column(name = "timestamp", nullable = false, updatable = false)
    private Instant timestamp;

    /**
     * Contexto adicional en JSON.
     * Ejemplo UPDATE: {"before": {"status": "ACTIVE"}, "after": {"status": "SUSPENDED"}}
     * columnDefinition = "jsonb" → PostgreSQL almacena como JSON binario (más eficiente).
     */
    @Column(name = "details", columnDefinition = "jsonb")
    private String details;
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. schema = "public" en @Table: obliga a Hibernate a usar el schema global
//    aunque esté configurado para multitenancy — auditoría siempre es global
// 2. @Builder de Lombok: AuditLogEntity.builder().tenantId("x").operation("CREATE").build()
//    sin constructor de 8 parámetros — mucho más legible
// 3. @CreatedDate: Spring Data audita el timestamp automáticamente al primer save()
//    Requiere @EntityListeners(AuditingEntityListener.class) en la entidad
//    y @EnableJpaAuditing en algún @Configuration (se agrega en pasos futuros)
// 4. columnDefinition = "jsonb": tipo específico de PostgreSQL — más eficiente que TEXT
//    para datos JSON porque permite queries dentro del JSON con operador ->
// 5. Inmutabilidad: updatable = false en timestamp — un log de auditoría no se edita
// ─────────────────────────────────────────────────────────────────────────────
