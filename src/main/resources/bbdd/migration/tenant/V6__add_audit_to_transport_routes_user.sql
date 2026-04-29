-- V6__add_audit_to_transport_routes_user.sql
-- Agregar columnas de auditoría a transport_routes_user

ALTER TABLE transport_routes_user
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';