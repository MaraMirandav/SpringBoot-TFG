package com.centros_sass.admin.domain.model;

/**
 * TenantStatus — estados del ciclo de vida de un tenant en la plataforma SaaS.
 *
 * En B2B SaaS, los clientes (tenants) pasan por estos estados:
 *   ACTIVE    → empresa activa — usuarios pueden login y usar el producto
 *   SUSPENDED → acceso bloqueado temporalmente (ej: factura impaga)
 *               Datos conservados — se reactiva al resolver el problema
 *   CANCELLED → contrato terminado definitivamente
 *               Datos pueden eliminarse tras período de retención legal
 *
 * Se guarda como VARCHAR(20) en BD → @Enumerated(EnumType.STRING) en la entidad.
 * Esto permite agregar estados futuros sin ALTER TYPE en PostgreSQL.
 */
public enum TenantStatus {

    /** Acceso completo al producto según el plan contratado. */
    ACTIVE,

    /**
     * Acceso bloqueado — datos conservados, se puede reactivar.
     * Caso típico: TenantFilter devuelve 403 si el tenant está SUSPENDED.
     */
    SUSPENDED,

    /** Contrato terminado. El tenant abandonó la plataforma o fue dado de baja. */
    CANCELLED
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. Enum en Java: tipo seguro — el compilador rechaza "ACTIVO" o cualquier typo
//    Vs. String status: con String puedes guardar "activo", "ACTIVE" o "Activo" sin error
// 2. @Enumerated(EnumType.STRING) en la entidad: guarda "ACTIVE" no 0 (índice ordinal)
//    EnumType.ORDINAL es peligroso — si reordenas el enum, los datos cambian de significado
// 3. Ciclo de vida SaaS: ACTIVE → SUSPENDED ↔ ACTIVE → CANCELLED
//    Este flujo define las reglas de negocio: qué puede hacer el tenant según su estado
// 4. VARCHAR(20) en BD + STRING en JPA: siempre coinciden → lectura directa sin mapeo extra
// ─────────────────────────────────────────────────────────────────────────────
