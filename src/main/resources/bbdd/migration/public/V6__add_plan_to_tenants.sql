-- Añadir plan_slug a tenants
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS plan_slug VARCHAR(50) NOT NULL DEFAULT 'basico';