package com.centros_sass.core.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * AuditLogRepository — acceso a la tabla audit_log en el schema public.
 *
 * Extiende JpaRepository: Spring Data genera automáticamente las operaciones
 * CRUD básicas sin escribir SQL. Heredamos save(), findById(), findAll(), etc.
 *
 * En arquitectura hexagonal este repositorio es un "output port":
 * la interfaz vive en el dominio (core.audit), la implementación la genera
 * Spring Data en tiempo de ejecución — el dominio no depende de JPA directamente.
 *
 * Los registros de auditoría son INMUTABLES: nunca se llama update() aquí.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {

    /**
     * Busca todos los registros de un tenant específico.
     * Spring Data genera el SQL desde el nombre: findBy + TenantId
     * → SELECT * FROM audit_log WHERE tenant_id = ?
     *
     * @param tenantId slug del tenant (ej: "acme", "globex")
     * @return historial de operaciones de ese tenant
     */
    List<AuditLogEntity> findByTenantId(String tenantId);

    /**
     * Historial completo de una entidad específica.
     * Útil para ver todos los cambios de la factura #123, del tenant "acme", etc.
     *
     * @param entityType "tenant", "user", "plan", "invoice"
     * @param entityId   ID de la entidad como String
     * @return lista de cambios de esa entidad, sin orden garantizado
     */
    List<AuditLogEntity> findByEntityTypeAndEntityId(String entityType, String entityId);

    /**
     * Registros en un rango de tiempo — útil para reportes y alertas de seguridad.
     * Between genera: WHERE timestamp BETWEEN :start AND :end
     *
     * @param start inicio del rango (inclusive)
     * @param end   fin del rango (inclusive)
     * @return operaciones ocurridas en ese intervalo
     */
    List<AuditLogEntity> findByTimestampBetween(Instant start, Instant end);
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. JpaRepository<T, ID>: T = tipo de entidad, ID = tipo de su PK
//    Hereda automáticamente: save(), findById(), findAll(), deleteById(), count()
// 2. Query derivado del nombre del método (Spring Data magic):
//    findByTenantId() → Spring genera SELECT * FROM audit_log WHERE tenant_id = ?
//    No necesitas @Query ni SQL manual para queries simples
// 3. findByEntityTypeAndEntityId: "And" en el nombre = WHERE entity_type = ? AND entity_id = ?
//    Spring Data lee el nombre y genera el WHERE automáticamente
// 4. @Repository: convierte excepciones JDBC/JPA a DataAccessException de Spring
//    Hace el código más portable — no depende de excepciones específicas de PostgreSQL
// ─────────────────────────────────────────────────────────────────────────────
