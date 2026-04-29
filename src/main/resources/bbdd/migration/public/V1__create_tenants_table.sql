-- ─────────────────────────────────────────────────────────────────────────────
-- V1 — Tabla "tenants" (schema public)
--
-- Esta tabla es el REGISTRO GLOBAL de todos los clientes (empresas) que usan
-- la plataforma SaasCon. Vive en el schema "public" porque es datos admin/global,
-- no datos de un tenant específico.
--
-- Cada tenant tiene un "slug" único (ej: "acme", "globex") que se usa para:
--   1. El nombre de su schema PostgreSQL: tenant_acme, tenant_globex
--   2. El claim "tenant_id" en su JWT token
--   3. URLs internas de la app (future: acme.saascon.com)
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE tenants (
    -- BIGSERIAL = autoincremento BIGINT (equivalente a @GeneratedValue en JPA)
    id          BIGSERIAL    PRIMARY KEY,

    -- Nombre de la empresa tal como se muestra en la UI
    name        VARCHAR(100) NOT NULL,

    -- Identificador URL-safe único: solo letras minúsculas, números y guiones
    -- Ejemplo: "acme-corp", "globex", "startup-xyz"
    -- Se valida con regex en el DTO: ^[a-z0-9-]+$
    slug        VARCHAR(50)  NOT NULL UNIQUE,

    -- Email del administrador principal de este tenant
    admin_email VARCHAR(255) NOT NULL,

    -- Estado del tenant: ACTIVE, SUSPENDED, CANCELLED
    -- VARCHAR(20) en lugar de ENUM para mayor flexibilidad en migraciones futuras
    status      VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',

    -- Timestamps de auditoría — manejados por Spring Data @CreatedDate/@LastModifiedDate
    -- TIMESTAMPTZ = timestamp WITH time zone (recomendado siempre sobre TIMESTAMP)
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- Índices para acelerar las búsquedas más frecuentes
-- slug: búsqueda al resolver tenant_id en cada request
-- status: filtrar tenants activos/suspendidos en el panel admin
CREATE INDEX idx_tenants_slug   ON tenants(slug);
CREATE INDEX idx_tenants_status ON tenants(status);

COMMENT ON TABLE tenants IS 'Registro global de clientes (empresas) que usan la plataforma SaasCon';
COMMENT ON COLUMN tenants.slug IS 'Identificador URL-safe único. Define el nombre del schema PostgreSQL: tenant_{slug}';
