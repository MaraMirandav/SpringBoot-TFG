package com.centros_sass.admin.domain.model;

/**
 * AdminUserRole — roles de los empleados internos de SaasCon con acceso al panel.
 *
 * IMPORTANTE: estos son roles de EMPLEADOS DE SAASCON — distintos de los roles
 * de los usuarios de cada tenant (que tienen OWNER/ADMIN/MEMBER/VIEWER en tenant_*.users).
 *
 * Jerarquía de privilegios (mayor a menor):
 *   SUPER_ADMIN → acceso total: crea/elimina tenants, planes y otros admins
 *   ADMIN       → acceso operacional: gestiona tenants y soporte, no crea admins
 *   SUPPORT     → solo lectura: ve tickets y datos, no puede modificar nada crítico
 *
 * DEFAULT 'SUPPORT' en la migración SQL aplica el principio de menor privilegio:
 * si no se especifica rol al crear un admin, recibe el mínimo acceso posible.
 */
public enum AdminUserRole {

    /**
     * Acceso completo. Solo fundadores o CTO.
     * Puede: crear/eliminar tenants, cambiar planes, gestionar otros admins.
     */
    SUPER_ADMIN,

    /**
     * Acceso operacional. Para el equipo de operaciones.
     * Puede: suspender tenants, responder soporte, ver métricas globales.
     * No puede: crear admins, eliminar datos críticos, cambiar precios.
     */
    ADMIN,

    /**
     * Solo lectura. Para el equipo de customer success.
     * Puede: ver tickets, datos de tenants para ayudar al cliente.
     * No puede: modificar configuraciones, planes ni datos de tenants.
     */
    SUPPORT
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. Roles admin ≠ roles tenant: confundirlos es el error más común en SaaS multitenant
//    AdminUserRole (quién gestiona la plataforma) ≠ roles en tenant_*.users
// 2. Principio de menor privilegio: DEFAULT 'SUPPORT' en SQL → acceso mínimo por defecto
//    Si el código de provisioning no especifica el rol, el admin no puede hacer daño
// 3. Documentación como código: el enum es más expresivo que una tabla en Confluence
//    Al leer el código sabes exactamente qué puede hacer cada rol
// 4. Futuro: @PreAuthorize("hasRole('SUPER_ADMIN')") en métodos críticos del panel
//    Spring Security usa este enum (como string) para verificar permisos en runtime
// ─────────────────────────────────────────────────────────────────────────────
