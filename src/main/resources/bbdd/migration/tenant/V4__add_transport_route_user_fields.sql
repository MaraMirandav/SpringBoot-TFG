-- Añadir columnas faltantes a transport_routes_user
ALTER TABLE transport_routes_user
    ADD COLUMN IF NOT EXISTS arrival_time TIME,
    ADD COLUMN IF NOT EXISTS uses_wheelchair BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS comment VARCHAR(500),
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- El CONSTRAINT requiere este bloque para no dar error si ya existe
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'uk_route_user') THEN
        ALTER TABLE transport_routes_user ADD CONSTRAINT uk_route_user UNIQUE (route_id, user_id);
    END IF;
END $$;