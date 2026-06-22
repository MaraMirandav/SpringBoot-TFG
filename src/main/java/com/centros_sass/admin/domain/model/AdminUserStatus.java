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
