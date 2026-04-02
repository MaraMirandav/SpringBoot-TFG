-- ============================================================
-- INSERT_ADMIN.SQL
-- Roles + Usuario Administrador inicial
-- Se ejecuta automáticamente al arrancar la aplicación
-- ============================================================

-- --------------------------------------------------------
-- ROLES
-- --------------------------------------------------------
INSERT INTO schema_template.roles_enum (role_name, created_at, created_by, updated_at, updated_by) VALUES ('ROLE_ADMIN',       CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_DIRECTOR',    CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_COORDINADOR', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_ENFERMERO',   CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_FISIO',       CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_TO',          CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_TS',          CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_PSICO',       CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_TAS',         CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_CONDUCTOR',   CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'), ('ROLE_COPILOTO',    CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system') ON CONFLICT (role_name) DO NOTHING;

-- --------------------------------------------------------
-- USUARIO ADMINISTRADOR
-- Password: admin.1234 (BCrypt)
-- --------------------------------------------------------
INSERT INTO schema_template.workers ( email, password, first_name, first_surname, dni_nie, main_phone, is_active, created_at, created_by, updated_at, updated_by ) VALUES ( 'admin@centro-sass.com', '$2a$10$TZPl2E/FDPmb5DdUWcJYVeMDBZT3apytGaiudle2NpYBhSz1uJqEG', 'Admin', 'Sistema', '00000000T', '+34 000000000', true, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' ) ON CONFLICT (email) DO NOTHING;

-- --------------------------------------------------------
-- ASIGNAR ROLE_ADMIN AL ADMIN
-- --------------------------------------------------------
INSERT INTO schema_template.workers_roles (worker_id, role_id) SELECT w.id, r.id FROM schema_template.workers w CROSS JOIN schema_template.roles_enum r WHERE w.email = 'admin@centro-sass.com' AND r.role_name = 'ROLE_ADMIN' ON CONFLICT DO NOTHING;
