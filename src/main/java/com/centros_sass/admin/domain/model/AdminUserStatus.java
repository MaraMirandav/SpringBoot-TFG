package com.centros_sass.admin.domain.model;

/**
 * AdminUserStatus — estado de la cuenta de un administrador interno.
 *
 * Solo dos valores porque los admins no "cancelan" su cuenta —
 * simplemente se desactivan cuando dejan la empresa.
 *
 * INACTIVE en vez de DELETE: si borramos el registro del admin, perdemos
 * la referencia en audit_log ("user_id = 42" sin saber quién era el 42).
 * Marcar como INACTIVE conserva la integridad del historial de auditoría.
 */
public enum AdminUserStatus {

    /** Cuenta activa — puede hacer login al panel de administración. */
    ACTIVE,

    /**
     * Cuenta desactivada — acceso bloqueado, datos conservados.
     * Caso típico: el empleado deja la empresa → se desactiva sin borrar.
     */
    INACTIVE
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. Soft delete vs hard delete: desactivar (INACTIVE) es mejor que borrar
//    en sistemas con auditoría — borrar rompe la referencia en audit_log
// 2. Enums pequeños también valen: aunque solo tiene 2 valores, evita que alguien
//    guarde "Inactive", "inactive" o "DISABLED" por error en el código
// ─────────────────────────────────────────────────────────────────────────────
