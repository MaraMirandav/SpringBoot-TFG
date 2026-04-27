package com.centros_sass.core.audit;

import com.centros_sass.core.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * AuditService — registra operaciones en la tabla audit_log.
 *
 * Responsabilidad única: recibir "qué pasó" y persistirlo.
 * Lo usan los use cases al crear, modificar o eliminar recursos.
 *
 * PATRÓN CRÍTICO — TenantContext es estático, NUNCA inyectado como bean:
 *   ✅ String tenantId = TenantContext.get();          ← llamada estática correcta
 *   ❌ private final TenantContext tenantContext;       ← lanzaría NoSuchBeanDefinitionException
 *
 * TenantContext es una utility class sin @Component — Spring no la registra.
 * Es igual a Math.abs() o Collections.emptyList(): se llama sin instanciar.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    // AuditLogRepository SÍ es un bean (@Repository) → se puede inyectar
    private final AuditLogRepository auditLogRepository;

    /**
     * Registra una operación de auditoría básica (sin contexto de cambio).
     * Uso típico: operaciones CREATE o acciones sin datos before/after.
     *
     * @param entityType tipo de entidad: "tenant", "user", "plan", "invoice"
     * @param entityId   ID de la entidad como String (acepta Long.toString() o UUID)
     * @param operation  acción: "CREATE", "UPDATE", "DELETE"
     */
    public void log(String entityType, String entityId, String operation) {
        // TenantContext.get() lee del ThreadLocal puesto por TenantFilter al inicio del request
        // LLAMADA ESTÁTICA — no inyectar como bean, TenantContext no tiene @Component
        String tenantId = TenantContext.get();

        // SecurityContextHolder: almacén estático (ThreadLocal) de Spring Security
        // auth.getName() devuelve el "subject" del JWT (email o ID del usuario)
        String userId = getCurrentUserId();

        AuditLogEntity entry = AuditLogEntity.builder()
                .tenantId(tenantId)
                .userId(userId)
                .entityType(entityType)
                .entityId(entityId)
                .operation(operation)
                // timestamp lo asigna @CreatedDate automáticamente al persistir
                .build();

        auditLogRepository.save(entry);

        // Parameterizado: evita concatenación de strings — SLF4J evalúa el mensaje
        // solo si el nivel INFO está habilitado → mejor rendimiento en producción
        log.info("Audit: tenant={} user={} {}:{} op={}",
                tenantId, userId, entityType, entityId, operation);
    }

    /**
     * Registra una operación con contexto adicional (útil para UPDATE y DELETE).
     *
     * @param entityType tipo de entidad afectada
     * @param entityId   ID de la entidad
     * @param operation  "UPDATE" o "DELETE"
     * @param details    JSON con contexto, ej: {"before":{"status":"ACTIVE"},"after":{"status":"SUSPENDED"}}
     */
    public void log(String entityType, String entityId, String operation, String details) {
        String tenantId = TenantContext.get();
        String userId = getCurrentUserId();

        AuditLogEntity entry = AuditLogEntity.builder()
                .tenantId(tenantId)
                .userId(userId)
                .entityType(entityType)
                .entityId(entityId)
                .operation(operation)
                .details(details)
                .build();

        auditLogRepository.save(entry);
        log.info("Audit: tenant={} user={} {}:{} op={} details={}",
                tenantId, userId, entityType, entityId, operation, details);
    }

    /**
     * Extrae el ID del usuario autenticado del SecurityContext.
     *
     * Spring Security popula el SecurityContext automáticamente al validar el JWT.
     * auth.getName() devuelve el "subject" del token (email o UUID del usuario).
     *
     * @return subject del JWT o "system" para operaciones sin usuario autenticado
     */
    private String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // "system" en lugar de null: audit_log.user_id permite NULL pero evitamos
        // valores ambiguos — "system" documenta que fue una operación automática
        return (auth != null && auth.getName() != null) ? auth.getName() : "system";
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. TenantContext.get() es ESTÁTICO: igual que Math.abs() — no se inyecta como bean
//    Patrón Utility Class: constructor privado, solo métodos estáticos, sin @Component
// 2. SecurityContextHolder: ThreadLocal de Spring Security — similar a TenantContext
//    Cada hilo tiene su Authentication sin interferir con otros requests simultáneos
// 3. Sobrecarga de métodos: dos versiones de log() — con y sin "details"
//    Más limpio que un parámetro @Nullable String details: el caller elige la firma
// 4. @Slf4j genera: private static final Logger log = LoggerFactory.getLogger(AuditService.class)
//    Lombok elimina ese boilerplate repetitivo en cada clase con logging
// 5. Constructor injection + @RequiredArgsConstructor: patrón recomendado en AGENTS.md
//    Spring inyecta auditLogRepository por constructor, no por campo
// ─────────────────────────────────────────────────────────────────────────────
