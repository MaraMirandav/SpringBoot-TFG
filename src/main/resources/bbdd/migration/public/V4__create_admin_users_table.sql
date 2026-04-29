-- ─────────────────────────────────────────────────────────────────────────────
-- V4 — Tabla "admin_users" (schema public)
--
-- Los "admin users" son los empleados internos de SaasCon que gestionan
-- la plataforma desde el panel admin. Son DISTINTOS de los usuarios de cada
-- tenant (que vivirán en los schemas tenant_*).
--
-- Separación importante:
--   admin_users  →  schema public  →  empleados de SaasCon
--   users        →  schema tenant_*  →  empleados/usuarios del cliente (tenant)
--
-- Roles disponibles:
--   SUPER_ADMIN — acceso completo: puede crear/eliminar tenants, planes y otros admins
--   ADMIN       — acceso operacional: gestiona tenants y soporte, no puede crear admins
--   SUPPORT     — solo lectura: ve tickets y datos de tenants, no modifica nada crítico
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE admin_users (
    id   BIGSERIAL PRIMARY KEY,

    -- Email usado como login — debe ser único en toda la tabla
    email VARCHAR(255) NOT NULL UNIQUE,

    -- Hash BCrypt de la contraseña — NUNCA almacenar contraseñas en texto plano
    -- BCrypt ya incluye la sal dentro del hash → no necesitamos columna "salt"
    password_hash VARCHAR(255) NOT NULL,

    -- Nombre completo del administrador para mostrar en la UI del panel
    full_name VARCHAR(150) NOT NULL,

    -- Rol del administrador — define qué puede hacer en el panel
    -- VARCHAR(20) para que sea fácil agregar roles futuros sin migración compleja
    -- DEFAULT 'SUPPORT' → principio de menor privilegio: si no se especifica, mínimo acceso
    role VARCHAR(20) NOT NULL DEFAULT 'SUPPORT',

    -- Estado de la cuenta — INACTIVE para deshabilitar sin borrar el historial de auditoría
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Índice UNIQUE en email — el login buscará por email, debe ser rápido
-- Ya existe la constraint UNIQUE arriba, pero el índice explícito da mejor visibilidad
CREATE UNIQUE INDEX idx_admin_users_email  ON admin_users(email);

-- Índices para filtrar administradores por rol y estado en el panel de gestión
CREATE INDEX idx_admin_users_role   ON admin_users(role);
CREATE INDEX idx_admin_users_status ON admin_users(status);

COMMENT ON TABLE admin_users IS 'Empleados internos de SaasCon con acceso al panel de administración. Distintos de los usuarios de cada tenant.';
COMMENT ON COLUMN admin_users.role IS 'SUPER_ADMIN (acceso total), ADMIN (operacional), SUPPORT (solo lectura)';
COMMENT ON COLUMN admin_users.password_hash IS 'Hash BCrypt (work factor 12). Nunca almacenar contraseña plana.';
