package com.centros_sass.core.audit;

import org.springframework.data.jpa.repository.JpaRepository;

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
