-- ─────────────────────────────────────────────────────────────────────────────
-- V2 — Tabla "plans" (schema public)
--
-- Catálogo de planes de suscripción disponibles para los tenants.
-- Vive en schema "public" porque es un catálogo global (no pertenece a un tenant).
--
-- Ejemplos de planes: Free (0$/mes, 5 usuarios), Pro (49$/mes, 50 usuarios),
-- Enterprise (199$/mes, usuarios ilimitados).
--
-- Un tenant tiene UN plan. La relación tenant → plan se agrega en V4 o posterior
-- cuando la entidad TenantEntity necesite la FK a plans.
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE plans (
    id   BIGSERIAL PRIMARY KEY,

    -- Nombre para mostrar en la UI: "Free", "Pro", "Enterprise"
    name VARCHAR(100) NOT NULL,

    -- Identificador interno URL-safe: "free", "pro", "enterprise"
    -- Usado en código para condicionar features: plan.slug.equals("pro")
    slug VARCHAR(50)  NOT NULL UNIQUE,

    -- Precio mensual en USD. NUMERIC(10,2) = hasta 99,999,999.99
    -- Usar NUMERIC (exacto) en vez de FLOAT (impreciso) para dinero — regla fundamental
    price_monthly NUMERIC(10, 2) NOT NULL DEFAULT 0.00,

    -- Límite de usuarios que puede tener un tenant con este plan
    -- -1 podría representar "ilimitado" en lógica de negocio futura
    max_users INT NOT NULL DEFAULT 5,

    -- Features habilitadas en este plan almacenadas como JSON
    -- Ejemplo: {"export_csv": true, "api_access": false, "custom_domain": true}
    -- JSONB = JSON binario (indexable, más eficiente que JSON texto)
    features JSONB,

    -- Estado: ACTIVE (visible en UI), DEPRECATED (no nuevos tenants), HIDDEN (interno)
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_plans_slug   ON plans(slug);
CREATE INDEX idx_plans_status ON plans(status);

-- Datos iniciales — los planes básicos siempre deben existir
-- INSERT en la misma migración garantiza consistencia: o todo o nada (transacción)
INSERT INTO plans (name, slug, price_monthly, max_users, features, status) VALUES
    ('Free',       'free',       0.00,   5,   '{"api_access": false, "export_csv": false}', 'ACTIVE'),
    ('Pro',        'pro',        49.00,  50,  '{"api_access": true,  "export_csv": true}',  'ACTIVE'),
    ('Enterprise', 'enterprise', 199.00, 100,  '{"api_access": true,  "export_csv": true, "custom_domain": true}', 'ACTIVE');

COMMENT ON TABLE plans IS 'Catálogo global de planes de suscripción. Cada tenant se suscribe a un plan.';
COMMENT ON COLUMN plans.features IS 'Mapa de features habilitadas para este plan en formato JSONB';
COMMENT ON COLUMN plans.max_users IS 'Máximo usuarios por tenant. -1 = ilimitado.';
