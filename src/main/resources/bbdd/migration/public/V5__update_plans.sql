-- Actualizar planes según nueva estructura de precios
ALTER TABLE plans ADD COLUMN IF NOT EXISTS max_workers INT NOT NULL DEFAULT 5;

DELETE FROM plans;

INSERT INTO plans (name, slug, price_monthly, max_workers, max_users, features, status) VALUES
('Básico',      'basico',       49.00,  5,  20,
 '{"asistencia": true, "actividades": true, "soporte_email": true}',
 'ACTIVE'),
('Profesional', 'profesional',  99.00,  10, 40,
 '{"asistencia": true, "actividades": true, "medicacion": true, "incidencias": true, "transporte": true, "soporte_prioritario": true}',
 'ACTIVE'),
('Enterprise',  'enterprise',   199.00, 25, 100,
 '{"asistencia": true, "actividades": true, "medicacion": true, "incidencias": true, "transporte": true, "modulos_personalizables": true, "soporte_24_7": true}',
 'ACTIVE');