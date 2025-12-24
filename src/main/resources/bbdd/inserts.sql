-- --------------------------------------------------------
-- TABLA DE TRABAJADORES (WORKERS)
-- Tabla: schema_template.workers
-- --------------------------------------------------------
INSERT INTO schema_template.workers
(first_name, second_name, first_surname, second_surname, email, password, main_phone, second_phone, is_active)
VALUES
-- 1. Trabajadora con todos los datos completos
('Ana', 'María', 'García', 'López', 'ana.garcia@email.com', 'contraseña123', '600123456', '911223344', true),

-- 2. Trabajador sin segundo nombre ni segundo teléfono (campos NULL)
('Carlos', NULL, 'Rodríguez', 'Sánchez', 'carlos.rod@email.com', 'claveSegura2025', '655987654', NULL, true),

-- 3. Trabajadora sin segundo apellido
('Lucía', 'Carmen', 'Fernández', NULL, 'lucia.fer@email.com', 'admin1234', '611222333', NULL, true),

-- 4. Trabajador inactivo (is_active = false)
('Miguel', NULL, 'Torres', 'Ruiz', 'miguel.torres@email.com', 'passMiguel', '699888777', '933445566', false),

-- 5. Trabajadora estándar
('Elena', NULL, 'Moreno', 'Díaz', 'elena.moreno@email.com', 'elena2025', '622333444', NULL, true);


-- --------------------------------------------------------
-- CATÁLOGO DE SEXOS
-- Tabla: sex_enum | Columna: sex
-- --------------------------------------------------------
INSERT INTO sex_enum (sex) VALUES ('Hombre')
ON CONFLICT (sex) DO NOTHING;

INSERT INTO sex_enum (sex) VALUES ('Mujer')
ON CONFLICT (sex) DO NOTHING;


-- --------------------------------------------------------
-- CATÁLOGO DE DEPENDENCIAS
-- Tabla: user_dependency_enum | Columna: nivel_dependency
-- --------------------------------------------------------
-- Opcional: Un valor por defecto para usuarios sanos
INSERT INTO user_dependency_enum (nivel_dependency) VALUES ('Sin Dependencia')
ON CONFLICT (nivel_dependency) DO NOTHING;

INSERT INTO user_dependency_enum (nivel_dependency) VALUES ('Baja')
ON CONFLICT (nivel_dependency) DO NOTHING;

INSERT INTO user_dependency_enum (nivel_dependency) VALUES ('Media')
ON CONFLICT (nivel_dependency) DO NOTHING;

INSERT INTO user_dependency_enum (nivel_dependency) VALUES ('Alta')
ON CONFLICT (nivel_dependency) DO NOTHING;

-- --------------------------------------------------------
-- TABLA DE USUARIOS (USERS)
-- Insertando claves foráneas directamente (1, 2, 3...)
-- --------------------------------------------------------

INSERT INTO users
(first_name, second_name, first_surname, second_surname, alias, dni, birth_date, is_active, sex_id, dependency_id)
VALUES
-- 1. Antonio: Hombre (1), Dependencia Baja (1)
(
    'Antonio', 'Luis', 'Martínez', 'Soria', 'Toño', '11223344B', '1950-03-15', true,
    1, -- sex_id
    1  -- dependency_id
),

-- 2. María: Mujer (2), Dependencia Alta (3)
(
    'María', 'Carmen', 'López', 'Vega', 'Mari', '55667788C', '1948-11-20', true,
    2, -- sex_id
    3  -- dependency_id
),

-- 3. José: Hombre (1), Dependencia Media (2)
(
    'José', NULL, 'Hernández', 'Ruiz', NULL, '99887766D', '1960-01-30', true, 
    1, -- sex_id
    2  -- dependency_id
),

-- 4. Dolores: Mujer (2), Sin Dependencia (4)
(
    'Dolores', NULL, 'Gómez', 'Pérez', 'Lola', '22334455E', '1955-07-12', false, 
    2, -- sex_id
    4  -- dependency_id
);


-- --------------------------------------------------------
-- TABLA DE OPEN_DAYS
-- Tabla: schema_template.open_days
-- --------------------------------------------------------

INSERT INTO open_days (day, open_at, close_at) VALUES
('Lunes', '08:15', '18:30'),
('Martes', '08:15', '18:30'),
('Miércoles', '08:15', '18:30'),
('Jueves', '08:15', '18:30'),
('Viernes', '08:15', '18:30');


-- --------------------------------------------------------
-- TABLA DE WORKERS_SCHEDULE
-- Tabla: schema_template.workers_schedules
-- --------------------------------------------------------
INSERT INTO workers_schedules (worker_id, day_id, start_at, end_at) VALUES
-- Ana García (ID 1)
(1, 1, '2025-01-06 08:15:00', '2025-01-06 18:30:00'),
(1, 2, '2025-01-07 08:15:00', '2025-01-07 18:30:00'),
(1, 3, '2025-01-08 08:15:00', '2025-01-08 18:30:00'),
(1, 4, '2025-01-09 08:15:00', '2025-01-09 18:30:00'),
(1, 5, '2025-01-10 08:15:00', '2025-01-10 18:30:00'),

-- Carlos Rodríguez (ID 2)
(2, 1, '2025-01-06 08:15:00', '2025-01-06 18:30:00'),
(2, 2, '2025-01-07 08:15:00', '2025-01-07 18:30:00'),
(2, 3, '2025-01-08 08:15:00', '2025-01-08 18:30:00'),
(2, 4, '2025-01-09 08:15:00', '2025-01-09 18:30:00'),
(2, 5, '2025-01-10 08:15:00', '2025-01-10 18:30:00'),

-- Lucía Fernández (ID 3) – Turno partido
-- Mañana
(3, 1, '2025-01-06 08:15:00', '2025-01-06 12:00:00'),
(3, 2, '2025-01-07 08:15:00', '2025-01-07 12:00:00'),
(3, 3, '2025-01-08 08:15:00', '2025-01-08 12:00:00'),
(3, 4, '2025-01-09 08:15:00', '2025-01-09 12:00:00'),
(3, 5, '2025-01-10 08:15:00', '2025-01-10 12:00:00'),
-- Tarde
(3, 1, '2025-01-06 15:00:00', '2025-01-06 18:30:00'),
(3, 2, '2025-01-07 15:00:00', '2025-01-07 18:30:00'),
(3, 3, '2025-01-08 15:00:00', '2025-01-08 18:30:00'),
(3, 4, '2025-01-09 15:00:00', '2025-01-09 18:30:00'),
(3, 5, '2025-01-10 15:00:00', '2025-01-10 18:30:00'),

-- Miguel Torres (ID 4) – Horario en ruta
-- Mañana
(4, 1, '2025-01-06 07:50:00', '2025-01-06 11:00:00'),
(4, 2, '2025-01-07 07:50:00', '2025-01-07 11:00:00'),
(4, 3, '2025-01-08 07:50:00', '2025-01-08 11:00:00'),
(4, 4, '2025-01-09 07:50:00', '2025-01-09 11:00:00'),
(4, 5, '2025-01-10 07:50:00', '2025-01-10 11:00:00'),
-- Tarde
(4, 1, '2025-01-06 16:00:00', '2025-01-06 19:15:00'),
(4, 2, '2025-01-07 16:00:00', '2025-01-07 19:15:00'),
(4, 3, '2025-01-08 16:00:00', '2025-01-08 19:15:00'),
(4, 4, '2025-01-09 16:00:00', '2025-01-09 19:15:00'),
(4, 5, '2025-01-10 16:00:00', '2025-01-10 19:15:00'),

-- Elena Moreno (ID 5)
(5, 1, '2025-01-06 08:15:00', '2025-01-06 18:30:00'),
(5, 2, '2025-01-07 08:15:00', '2025-01-07 18:30:00'),
(5, 3, '2025-01-08 08:15:00', '2025-01-08 18:30:00'),
(5, 4, '2025-01-09 08:15:00', '2025-01-09 18:30:00'),
(5, 5, '2025-01-10 08:15:00', '2025-01-10 18:30:00');


-- --------------------------------------------------------
-- TABLA DE WORKERS_SCHEDULES_RECORDS
-- Tabla: schema_template.workers_schedules_records
-- --------------------------------------------------------

-- ANA GARCÍA (ID 1)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active) VALUES
(1, 1, '2025-01-13 08:17', '2025-01-13 18:28', false),
(1, 2, '2025-01-14 08:12', '2025-01-14 18:35', false),
(1, 3, '2025-01-15 08:20', '2025-01-15 18:25', false),
(1, 4, '2025-01-16 08:10', '2025-01-16 18:32', false),
(1, 5, '2025-01-17 08:18', NULL, true);


-- CARLOS RODRÍGUEZ (ID 2)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active) VALUES
(2, 6, '2025-01-13 08:14', '2025-01-13 18:31', false),
(2, 7, '2025-01-14 08:19', '2025-01-14 18:27', false),
(2, 8, '2025-01-15 08:11', '2025-01-15 18:29', false),
(2, 9, '2025-01-16 08:17', '2025-01-16 18:34', false),
(2, 10, '2025-01-17 08:16', NULL, true);

-- LUCÍA FERNÁNDEZ (ID 3)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active) VALUES
-- Lunes
(3, 11, '2025-01-13 08:16', '2025-01-13 12:02', false),
(3, 16, '2025-01-13 15:03', '2025-01-13 18:28', false),

-- Martes
(3, 12, '2025-01-14 08:12', '2025-01-14 11:58', false),
(3, 17, '2025-01-14 15:05', '2025-01-14 18:33', false),

-- Miércoles
(3, 13, '2025-01-15 08:20', '2025-01-15 12:01', false),
(3, 18, '2025-01-15 15:02', '2025-01-15 18:29', false),

-- Jueves
(3, 14, '2025-01-16 08:14', '2025-01-16 12:03', false),
(3, 19, '2025-01-16 15:01', '2025-01-16 18:31', false),

-- Viernes (solo mañana, abierto)
(3, 15, '2025-01-17 08:17', NULL, true);


-- MIGUEL TORRES (ID 4)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active) VALUES
-- Lunes
(4, 21, '2025-01-13 07:52', '2025-01-13 11:03', false),
(4, 26, '2025-01-13 16:02', '2025-01-13 19:12', false),

-- Martes
(4, 22, '2025-01-14 07:49', '2025-01-14 10:58', false),
(4, 27, '2025-01-14 16:05', '2025-01-14 19:17', false),

-- Miércoles
(4, 23, '2025-01-15 07:55', '2025-01-15 11:01', false),
(4, 28, '2025-01-15 16:03', '2025-01-15 19:14', false),

-- Jueves
(4, 24, '2025-01-16 07:51', '2025-01-16 11:04', false),
(4, 29, '2025-01-16 16:01', '2025-01-16 19:13', false),

-- Viernes (solo mañana, abierto)
(4, 25, '2025-01-17 07:53', NULL, true);


-- ELENA MORENO (ID 5)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active) VALUES
(5, 31, '2025-01-13 08:18', '2025-01-13 18:27', false),
(5, 32, '2025-01-14 08:13', '2025-01-14 18:34', false),
(5, 33, '2025-01-15 08:21', '2025-01-15 18:29', false),
(5, 34, '2025-01-16 08:16', '2025-01-16 18:33', false),
-- Viernes (solo mañana, abierto)
(5, 35, '2025-01-17 08:14', NULL, true);
