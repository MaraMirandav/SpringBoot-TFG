-- ─────────────────────────────────────────────────────────────────────────────
-- V3 — Tabla "audit_log" (schema public)
--
-- Registro inmutable de todas las operaciones CREATE, UPDATE y DELETE del sistema.
-- Vive en schema "public" aunque registra operaciones de todos los tenants.
-- Esto permite queries de auditoría cross-tenant desde el panel admin.
--
-- INMUTABLE por diseño: NUNCA se hace UPDATE ni DELETE sobre esta tabla.
-- Si necesitas corregir un registro, se agrega una fila nueva con la corrección.
-- Esto garantiza integridad del historial de auditoría.
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE audit_log (
    id BIGSERIAL PRIMARY KEY,

    -- De qué tenant proviene la acción
    -- VARCHAR en vez de FK a tenants.slug porque el tenant podría eliminarse
    -- y queremos conservar el historial aunque el tenant ya no exista
    tenant_id   VARCHAR(100) NOT NULL,

    -- Quién hizo la acción (subject del JWT — normalmente el UUID o email del usuario)
    -- NULL permitido para operaciones de sistema sin usuario autenticado (ej: Flyway, schedulers)
    user_id     VARCHAR(255),

    -- Tipo de entidad afectada: "tenant", "plan", "user", "invoice", etc.
    entity_type VARCHAR(100) NOT NULL,

    -- ID de la entidad afectada — usamos VARCHAR para soportar UUIDs y BIGINTs
    entity_id   VARCHAR(255) NOT NULL,

    -- Operación realizada: CREATE, UPDATE, DELETE, LOGIN, LOGOUT, etc.
    operation   VARCHAR(20)  NOT NULL,

    -- Datos adicionales del cambio en formato JSONB
    -- Ejemplo UPDATE: {"before": {"status": "ACTIVE"}, "after": {"status": "SUSPENDED"}}
    -- Ejemplo CREATE: {"name": "Acme Corp", "slug": "acme"}
    details     JSONB,

    -- Timestamp exacto de la operación — NO usar DEFAULT NOW() desde Java
    -- porque el reloj de la JVM puede diferir del DB. DEFAULT NOW() garantiza
    -- que sea el timestamp del DB (más confiable para auditoría).
    timestamp   TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- ── Índices de auditoría ──────────────────────────────────────────────────────
-- tenant_id: filtrar todas las acciones de un tenant específico
-- (entity_type, entity_id): ver historial completo de una entidad
-- timestamp: queries por rango de fechas (reportes, alertas de seguridad)
CREATE INDEX idx_audit_log_tenant_id ON audit_log(tenant_id);
CREATE INDEX idx_audit_log_entity    ON audit_log(entity_type, entity_id);
CREATE INDEX idx_audit_log_timestamp ON audit_log(timestamp);

COMMENT ON TABLE audit_log IS 'Registro inmutable de todas las operaciones. NUNCA modificar registros existentes.';
COMMENT ON COLUMN audit_log.details IS 'JSON con contexto de la operación. Para UPDATE incluye valores before/after.';
