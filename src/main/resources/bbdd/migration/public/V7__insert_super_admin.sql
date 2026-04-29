-- Insertar admin interno para acceso al panel admin
-- Email: admin@saascon.com
-- Password temporal: admin.1234 (BCrypt)
-- Rol: SUPER_ADMIN (acceso total al panel)
INSERT INTO admin_users (email, password_hash, full_name, role, status, created_at, updated_at)
VALUES (
    'admin@saascon.com',
    '$2a$10$TZPl2E/FDPmb5DdUWcJYVeMDBZT3apytGaiudle2NpYBhSz1uJqEG',
    'Admin SaasCon',
    'SUPER_ADMIN',
    'ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
) ON CONFLICT (email) DO NOTHING;