CREATE SCHEMA IF NOT EXISTS schema_template;
-- Opcional: Establecer este schema como el predeterminado para el usuario
ALTER ROLE postgres SET search_path TO schema_template;