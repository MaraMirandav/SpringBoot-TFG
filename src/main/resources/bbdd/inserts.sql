-- --------------------------------------------------------
-- TABLA DE TRABAJADORES (WORKERS)
-- Tabla: schema_template.workers
-- --------------------------------------------------------
INSERT INTO workers
(first_name, second_name, first_surname, second_surname, dni_nie, email, password, main_phone, second_phone, is_active, created_at, updated_at)
VALUES
    -- 1. Trabajadora con todos los datos completos
    ('Ana', 'María', 'García', 'López', 'Z2033087A', 'ana.garcia@email.com', 'contraseña123', '600123456', '911223344', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 2. Trabajador sin segundo nombre ni segundo teléfono (campos NULL)
    ('Carlos', NULL, 'Rodríguez', 'Sánchez', 'Z2333087A', 'carlos.rod@email.com', 'claveSegura2025', '655987654', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 3. Trabajadora sin segundo apellido
    ('Lucía', 'Carmen', 'Fernández', NULL, 'Z2033037C', 'lucia.fer@email.com', 'admin1234', '611222333', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 4. Trabajador inactivo (is_active = false)
    ('Miguel', NULL, 'Torres', 'Ruiz', 'Z2033083J', 'miguel.torres@email.com', 'passMiguel', '699888777', '933445566', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 5. Trabajadora estándar
    ('Elena', NULL, 'Moreno', 'Díaz', 'Z2343087A', 'elena.moreno@email.com', 'elena2025', '622333444', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Roberto', NULL, 'Sanz', 'Gil', 'Z2033547T', 'roberto.rrhh@email.com', 'pass123', '600666666', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Beatriz', NULL, 'Molina', 'Pérez', 'Z2036687F', 'bea.recep@email.com', 'pass123', '600777777', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Laura', NULL, 'Ruiz', 'Giménez', 'Z2273087A', 'laura.fisio@email.com', 'pass123', '600888888', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Pablo', 'José', 'Martín', 'Sanz', 'Z2038487B', 'pablo.to@email.com', 'pass123', '600999999', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Sara', NULL, 'Díaz', 'Romero', 'Z2033447A', 'sara.ts@email.com', 'pass123', '611000111', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('David', NULL, 'Serrano', 'Molina', 'Z2333087Y', 'david.psico@email.com', 'pass123', '611222333', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Carmen', 'Luisa', 'Ortiz', 'Vega', 'Z2233087A', 'carmen.gero@email.com', 'pass123', '611333444', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Javier', NULL, 'Navarro', 'Flores', 'Z2133087Y', 'javier.tas@email.com', 'pass123', '611444555', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Manuel', 'Antonio', 'Iglesias', 'Vargas', 'Z2035587A', 'manolo.ruta@email.com', 'pass123', '611555666', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Julián', NULL, 'Vázquez', 'Rey', 'Z2036697A', 'julian.copi@email.com', 'pass123', '611666777', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Rosa', 'María', 'Vidal', 'Sáez', 'Z2037787A', 'rosa.limpieza@email.com', 'pass123', '611777888', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATÁLOGO DE SEXOS
-- Tabla: sex_enum | Columna: sex
-- --------------------------------------------------------
INSERT INTO sex_enum (sex, created_at, updated_at)
VALUES
    ('Hombre', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Mujer', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (sex) DO NOTHING;

-- --------------------------------------------------------
-- CATÁLOGO DE DEPENDENCIAS
-- Tabla: user_dependency_enum | Columna: nivel_dependency
-- --------------------------------------------------------
-- Opcional: Un valor por defecto para usuarios sanos
INSERT INTO user_dependency_enum (nivel_dependency, created_at, updated_at)
VALUES
    ('Sin Dependencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Baja', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Media', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Alta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (nivel_dependency) DO NOTHING;

-- --------------------------------------------------------
-- TABLA DE USUARIOS (USERS)
-- Insertando claves foráneas directamente (1, 2, 3...)
-- --------------------------------------------------------

INSERT INTO users
    (first_name, second_name, first_surname, second_surname, alias, email, phone, cellphone, dni_nie, birth_date, is_active, sex_id, dependency_id, created_at, updated_at)
VALUES
    -- 1. Antonio: Hombre (1), Dependencia Baja (1)
    ('Antonio', 'Luis', 'Martínez', 'Soria', 'Toño', NULL, NULL, NULL, '11223344B', '1950-03-15', true, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 2. María: Mujer (2), Dependencia Alta (3)
    ('María', 'Carmen', 'López', 'Vega', 'Mari', NULL, NULL, NULL, '55667788C', '1948-11-20', true, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 3. José: Hombre (1), Dependencia Media (2)
    ('José', NULL, 'Hernández', 'Ruiz', NULL, NULL, NULL, NULL, '99887766D', '1960-01-30', true, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 4. Dolores: Mujer (2), Sin Dependencia (4)
    ('Dolores', NULL, 'Gómez', 'Pérez', 'Lola', NULL, NULL, NULL, '22334455E', '1955-07-12', false, 2, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- TABLA DE OPEN_DAYS
-- Tabla: schema_template.open_days
-- --------------------------------------------------------

INSERT INTO open_days (day_name, open_at, close_at, created_at, updated_at)
VALUES
    ('Lunes', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Martes', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Miércoles', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Jueves', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Viernes', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON CONFLICT (day_name) DO NOTHING;


-- --------------------------------------------------------
-- TABLA DE WORKERS_SCHEDULE
-- Tabla: schema_template.workers_schedules
-- --------------------------------------------------------
INSERT INTO workers_schedules (worker_id, day_id, start_at, end_at, created_at, updated_at)
VALUES
    -- Ana García (ID 1) - Directora
    (1, 1, '2025-01-06 08:15:00', '2025-01-06 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ID 1
    (1, 2, '2025-01-07 08:15:00', '2025-01-07 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 3, '2025-01-08 08:15:00', '2025-01-08 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 4, '2025-01-09 08:15:00', '2025-01-09 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 5, '2025-01-10 08:15:00', '2025-01-10 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Carlos Rodríguez (ID 2) - Coord/TAS
    (2, 1, '2025-01-06 08:15:00', '2025-01-06 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, '2025-01-07 08:15:00', '2025-01-07 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 3, '2025-01-08 08:15:00', '2025-01-08 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 4, '2025-01-09 08:15:00', '2025-01-09 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 5, '2025-01-10 08:15:00', '2025-01-10 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Lucía Fernández (ID 3) – Turno partido
    (3, 1, '2025-01-06 08:15:00', '2025-01-06 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 2, '2025-01-07 08:15:00', '2025-01-07 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, '2025-01-08 08:15:00', '2025-01-08 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 4, '2025-01-09 08:15:00', '2025-01-09 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 5, '2025-01-10 08:15:00', '2025-01-10 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 1, '2025-01-06 15:00:00', '2025-01-06 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 2, '2025-01-07 15:00:00', '2025-01-07 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, '2025-01-08 15:00:00', '2025-01-08 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 4, '2025-01-09 15:00:00', '2025-01-09 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 5, '2025-01-10 15:00:00', '2025-01-10 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Miguel Torres (ID 4) – Horario en ruta (Partido)
    (4, 1, '2025-01-06 07:50:00', '2025-01-06 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 2, '2025-01-07 07:50:00', '2025-01-07 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 3, '2025-01-08 07:50:00', '2025-01-08 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, '2025-01-09 07:50:00', '2025-01-09 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 5, '2025-01-10 07:50:00', '2025-01-10 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 1, '2025-01-06 16:00:00', '2025-01-06 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 2, '2025-01-07 16:00:00', '2025-01-07 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 3, '2025-01-08 16:00:00', '2025-01-08 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, '2025-01-09 16:00:00', '2025-01-09 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 5, '2025-01-10 16:00:00', '2025-01-10 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Elena Moreno (ID 5) - Enfermera
    (5, 1, '2025-01-06 08:15:00', '2025-01-06 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 2, '2025-01-07 08:15:00', '2025-01-07 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 3, '2025-01-08 08:15:00', '2025-01-08 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 4, '2025-01-09 08:15:00', '2025-01-09 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 5, '2025-01-10 08:15:00', '2025-01-10 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Roberto Sanz (ID 6 - RRHH/TO) - Horario Oficina
    (6, 1, '2025-01-06 09:00:00', '2025-01-06 18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 2, '2025-01-07 09:00:00', '2025-01-07 18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 3, '2025-01-08 09:00:00', '2025-01-08 18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 4, '2025-01-09 09:00:00', '2025-01-09 18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 5, '2025-01-10 09:00:00', '2025-01-10 18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Beatriz Molina (ID 7 - Recepción) - Horario Entrada
    (7, 1, '2025-01-06 08:00:00', '2025-01-06 16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 2, '2025-01-07 08:00:00', '2025-01-07 16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 3, '2025-01-08 08:00:00', '2025-01-08 16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 4, '2025-01-09 08:00:00', '2025-01-09 16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 5, '2025-01-10 08:00:00', '2025-01-10 16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Laura Ruiz (ID 8 - Fisio) - Horario Mañanas
    (8, 1, '2025-01-06 08:30:00', '2025-01-06 14:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 2, '2025-01-07 08:30:00', '2025-01-07 14:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 3, '2025-01-08 08:30:00', '2025-01-08 14:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 4, '2025-01-09 08:30:00', '2025-01-09 14:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 5, '2025-01-10 08:30:00', '2025-01-10 14:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Pablo Martín (ID 9 - TO) - Horario Partido
    (9, 1, '2025-01-06 10:00:00', '2025-01-06 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 2, '2025-01-07 10:00:00', '2025-01-07 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 3, '2025-01-08 10:00:00', '2025-01-08 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 4, '2025-01-09 10:00:00', '2025-01-09 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 5, '2025-01-10 10:00:00', '2025-01-10 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 1, '2025-01-06 15:30:00', '2025-01-06 19:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 2, '2025-01-07 15:30:00', '2025-01-07 19:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 3, '2025-01-08 15:30:00', '2025-01-08 19:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 4, '2025-01-09 15:30:00', '2025-01-09 19:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 5, '2025-01-10 15:30:00', '2025-01-10 19:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Sara Díaz (ID 10 - TS)
    (10, 1, '2025-01-06 09:00:00', '2025-01-06 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 2, '2025-01-07 09:00:00', '2025-01-07 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 3, '2025-01-08 09:00:00', '2025-01-08 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 4, '2025-01-09 09:00:00', '2025-01-09 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 5, '2025-01-10 09:00:00', '2025-01-10 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- David Serrano (ID 11 - Psico)
    (11, 1, '2025-01-06 09:00:00', '2025-01-06 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 2, '2025-01-07 09:00:00', '2025-01-07 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 3, '2025-01-08 09:00:00', '2025-01-08 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 4, '2025-01-09 09:00:00', '2025-01-09 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 5, '2025-01-10 09:00:00', '2025-01-10 14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Carmen Ortiz (ID 12 - TAS) - Turno Mañana
    (12, 1, '2025-01-06 07:00:00', '2025-01-06 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 2, '2025-01-07 07:00:00', '2025-01-07 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 3, '2025-01-08 07:00:00', '2025-01-08 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 4, '2025-01-09 07:00:00', '2025-01-09 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 5, '2025-01-10 07:00:00', '2025-01-10 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Javier Navarro (ID 13 - TAS) - Turno Tarde
    (13, 1, '2025-01-06 15:00:00', '2025-01-06 22:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 2, '2025-01-07 15:00:00', '2025-01-07 22:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 3, '2025-01-08 15:00:00', '2025-01-08 22:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 4, '2025-01-09 15:00:00', '2025-01-09 22:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 5, '2025-01-10 15:00:00', '2025-01-10 22:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Manolo Iglesias (ID 14 - Conductor) - Ruta Partida
    (14, 1, '2025-01-06 07:50:00', '2025-01-06 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 2, '2025-01-07 07:50:00', '2025-01-07 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 3, '2025-01-08 07:50:00', '2025-01-08 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 4, '2025-01-09 07:50:00', '2025-01-09 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 5, '2025-01-10 07:50:00', '2025-01-10 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 1, '2025-01-06 16:00:00', '2025-01-06 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 2, '2025-01-07 16:00:00', '2025-01-07 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 3, '2025-01-08 16:00:00', '2025-01-08 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 4, '2025-01-09 16:00:00', '2025-01-09 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 5, '2025-01-10 16:00:00', '2025-01-10 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Julián Vázquez (ID 15 - Copiloto) - Ruta Partida
    (15, 1, '2025-01-06 07:50:00', '2025-01-06 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 2, '2025-01-07 07:50:00', '2025-01-07 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 3, '2025-01-08 07:50:00', '2025-01-08 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 4, '2025-01-09 07:50:00', '2025-01-09 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 5, '2025-01-10 07:50:00', '2025-01-10 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 1, '2025-01-06 16:00:00', '2025-01-06 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 2, '2025-01-07 16:00:00', '2025-01-07 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 3, '2025-01-08 16:00:00', '2025-01-08 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 4, '2025-01-09 16:00:00', '2025-01-09 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 5, '2025-01-10 16:00:00', '2025-01-10 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Rosa Vidal (ID 16 - Limpieza)
    (16, 1, '2025-01-06 07:00:00', '2025-01-06 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 2, '2025-01-07 07:00:00', '2025-01-07 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 3, '2025-01-08 07:00:00', '2025-01-08 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 4, '2025-01-09 07:00:00', '2025-01-09 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 5, '2025-01-10 07:00:00', '2025-01-10 15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- TABLA DE WORKERS_SCHEDULES_RECORDS
-- --------------------------------------------------------
-- ANA GARCÍA (ID 1)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (1, 1, '2025-01-13 08:17', '2025-01-13 18:28', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 2, '2025-01-14 08:12', '2025-01-14 18:35', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 3, '2025-01-15 08:20', '2025-01-15 18:25', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 4, '2025-01-16 08:10', '2025-01-16 18:32', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 5, '2025-01-17 08:18', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- CARLOS RODRÍGUEZ (ID 2)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (2, 6, '2025-01-13 08:14', '2025-01-13 18:31', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 7, '2025-01-14 08:19', '2025-01-14 18:27', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 8, '2025-01-15 08:11', '2025-01-15 18:29', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 9, '2025-01-16 08:17', '2025-01-16 18:34', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 10, '2025-01-17 08:16', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- LUCÍA FERNÁNDEZ (ID 3)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    -- Lunes
    (3, 11, '2025-01-13 08:16', '2025-01-13 12:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 16, '2025-01-13 15:03', '2025-01-13 18:28', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Martes
    (3, 12, '2025-01-14 08:12', '2025-01-14 11:58', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 17, '2025-01-14 15:05', '2025-01-14 18:33', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Miércoles
    (3, 13, '2025-01-15 08:20', '2025-01-15 12:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 18, '2025-01-15 15:02', '2025-01-15 18:29', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Jueves
    (3, 14, '2025-01-16 08:14', '2025-01-16 12:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 19, '2025-01-16 15:01', '2025-01-16 18:31', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Viernes
    (3, 15, '2025-01-17 08:17', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- MIGUEL TORRES (ID 4)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    -- Lunes
    (4, 21, '2025-01-13 07:52', '2025-01-13 11:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 26, '2025-01-13 16:02', '2025-01-13 19:12', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Martes
    (4, 22, '2025-01-14 07:49', '2025-01-14 10:58', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 27, '2025-01-14 16:05', '2025-01-14 19:17', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Miércoles
    (4, 23, '2025-01-15 07:55', '2025-01-15 11:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 28, '2025-01-15 16:03', '2025-01-15 19:14', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Jueves
    (4, 24, '2025-01-16 07:51', '2025-01-16 11:04', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 29, '2025-01-16 16:01', '2025-01-16 19:13', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Viernes
    (4, 25, '2025-01-17 07:53', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ELENA MORENO (ID 5)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (5, 31, '2025-01-13 08:18', '2025-01-13 18:27', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 32, '2025-01-14 08:13', '2025-01-14 18:34', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 33, '2025-01-15 08:21', '2025-01-15 18:29', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 34, '2025-01-16 08:16', '2025-01-16 18:33', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 35, '2025-01-17 08:14', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ROBERTO SANZ (ID 6 - RRHH/TO)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (6, 36, '2025-01-13 08:55', '2025-01-13 18:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 37, '2025-01-14 09:02', '2025-01-14 18:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 38, '2025-01-15 08:58', '2025-01-15 18:10', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 39, '2025-01-16 09:00', '2025-01-16 18:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 40, '2025-01-17 08:57', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- BEATRIZ MOLINA (ID 7 - Recepción)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (7, 41, '2025-01-13 07:55', '2025-01-13 16:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 42, '2025-01-14 07:58', '2025-01-14 16:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 43, '2025-01-15 07:52', '2025-01-15 16:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 44, '2025-01-16 07:59', '2025-01-16 16:00', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 45, '2025-01-17 07:56', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- LAURA RUIZ (ID 8 - Fisio)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (8, 46, '2025-01-13 08:25', '2025-01-13 14:35', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 47, '2025-01-14 08:31', '2025-01-14 14:32', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 48, '2025-01-15 08:28', '2025-01-15 14:38', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 49, '2025-01-16 08:29', '2025-01-16 14:31', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 50, '2025-01-17 08:26', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PABLO MARTÍN (ID 9 - TO)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    -- Lunes
    (9, 51, '2025-01-13 09:58', '2025-01-13 14:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 56, '2025-01-13 15:28', '2025-01-13 19:32', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Martes
    (9, 52, '2025-01-14 10:01', '2025-01-14 14:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 57, '2025-01-14 15:32', '2025-01-14 19:31', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Miércoles
    (9, 53, '2025-01-15 09:59', '2025-01-15 14:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 58, '2025-01-15 15:29', '2025-01-15 19:35', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Jueves
    (9, 54, '2025-01-16 10:02', '2025-01-16 14:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 59, '2025-01-16 15:31', '2025-01-16 19:33', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Viernes
    (9, 55, '2025-01-17 09:55', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- SARA DÍAZ (ID 10 - TS)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (10, 61, '2025-01-13 09:05', '2025-01-13 17:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 62, '2025-01-14 08:58', '2025-01-14 17:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 63, '2025-01-15 08:59', '2025-01-15 17:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 64, '2025-01-16 09:02', '2025-01-16 17:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 65, '2025-01-17 09:01', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- DAVID SERRANO (ID 11 - Psico)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (11, 66, '2025-01-13 08:55', '2025-01-13 14:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 67, '2025-01-14 09:05', '2025-01-14 14:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 68, '2025-01-15 08:58', '2025-01-15 14:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 69, '2025-01-16 08:59', '2025-01-16 14:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 70, '2025-01-17 09:00', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- CARMEN ORTIZ (ID 12 - TAS)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (12, 71, '2025-01-13 06:55', '2025-01-13 15:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 72, '2025-01-14 06:58', '2025-01-14 15:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 73, '2025-01-15 06:59', '2025-01-15 15:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 74, '2025-01-16 07:01', '2025-01-16 15:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 75, '2025-01-17 06:56', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- JAVIER NAVARRO (ID 13 - TAS)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (13, 76, '2025-01-13 14:55', '2025-01-13 22:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 77, '2025-01-14 14:58', '2025-01-14 22:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 78, '2025-01-15 14:59', '2025-01-15 22:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 79, '2025-01-16 15:01', '2025-01-16 22:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 80, '2025-01-17 14:57', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- MANOLO IGLESIAS (ID 14 - Conductor)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    -- Lunes
    (14, 81, '2025-01-13 07:45', '2025-01-13 11:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 86, '2025-01-13 15:55', '2025-01-13 19:20', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Martes
    (14, 82, '2025-01-14 07:48', '2025-01-14 11:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 87, '2025-01-14 15:58', '2025-01-14 19:18', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Miércoles
    (14, 83, '2025-01-15 07:49', '2025-01-15 11:01', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 88, '2025-01-15 16:01', '2025-01-15 19:16', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Jueves
    (14, 84, '2025-01-16 07:51', '2025-01-16 11:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 89, '2025-01-16 15:59', '2025-01-16 19:19', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Viernes
    (14, 85, '2025-01-17 07:46', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- JULIÁN VÁZQUEZ (ID 15 - Copiloto)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    -- Lunes
    (15, 91, '2025-01-13 07:48', '2025-01-13 11:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 96, '2025-01-13 15:58', '2025-01-13 19:18', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Martes
    (15, 92, '2025-01-14 07:50', '2025-01-14 11:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 97, '2025-01-14 15:55', '2025-01-14 19:20', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Miércoles
    (15, 93, '2025-01-15 07:51', '2025-01-15 11:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 98, '2025-01-15 16:00', '2025-01-15 19:15', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Jueves
    (15, 94, '2025-01-16 07:52', '2025-01-16 11:04', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 99, '2025-01-16 15:58', '2025-01-16 19:17', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Viernes
    (15, 95, '2025-01-17 07:49', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ROSA VIDAL (ID 16 - Limpieza)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (16, 101, '2025-01-13 06:58', '2025-01-13 15:02', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 102, '2025-01-14 07:01', '2025-01-14 15:05', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 103, '2025-01-15 06:55', '2025-01-15 15:00', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 104, '2025-01-16 07:02', '2025-01-16 15:03', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 105, '2025-01-17 06:59', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATÁLOGOS DE CENTRO (Tipos y Gravedad)
-- --------------------------------------------------------
-- Tipos de Incidencia de CENTRO
INSERT INTO incident_cd_enum (incident_name, created_at, updated_at) VALUES
    ('Mantenimiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Limpieza', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Suministros', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON CONFLICT (incident_name) DO NOTHING;

-- Gravedad de CENTRO
INSERT INTO significance_cd_enum (significance_name, created_at, updated_at) VALUES
    ('Baja', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Media', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Alta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON CONFLICT (significance_name) DO NOTHING;

-- --------------------------------------------------------
-- CATÁLOGOS DE USUARIO (Tipos y Gravedad)
-- --------------------------------------------------------
-- Tipos de Incidencia de USUARIO
INSERT INTO incident_user_enum (incident_name, created_at, updated_at) VALUES
    ('Salud / Caída', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Comportamiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Medicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON CONFLICT (incident_name) DO NOTHING;

-- Gravedad de USUARIO
INSERT INTO significance_user_enum (significance_name, created_at, updated_at) VALUES
    ('Leve', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Moderada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Crítica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON CONFLICT (significance_name) DO NOTHING;

-- --------------------------------------------------------
-- CATÁLOGO: ESTADOS DE INCIDENCIA
-- --------------------------------------------------------
INSERT INTO incident_status_enum (status_name, created_at, updated_at) VALUES
('Activa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('En revisión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cerrada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cancelada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (status_name) DO NOTHING;

-- --------------------------------------------------------
-- INCIDENCIAS DE CENTRO
-- --------------------------------------------------------
-- 1. Caldera: Estado 'En revisión' (ID 2)
INSERT INTO cd_incidents
(incident_cd_id, significance_cd_id, created_by_worker_id, incident_status_id, comment, created_at, updated_at)
VALUES
    (1, 3, 1, 2, 'La caldera hace un ruido extraño y pierde agua.', '2025-01-14 09:00:00', CURRENT_TIMESTAMP);

-- 2. Café: Estado 'Cerrada' (ID 3)
INSERT INTO cd_incidents
(incident_cd_id, significance_cd_id, created_by_worker_id, incident_status_id, comment, created_at, updated_at)
VALUES
    (2, 1, 5, 3, 'Se ha derramado café en la sala de estar.', '2025-01-14 16:00:00', CURRENT_TIMESTAMP);

--- --------------------------------------------------------
-- INCIDENCIAS DE USUARIO (users_incidents)
-- --------------------------------------------------------
-- 1. Antonio (Caída): Estado 'En revisión' (ID 2)
INSERT INTO users_incidents (user_id, incident_user_id, significance_user_id, created_by_worker_id, incident_status_id, comment, created_at, updated_at)
VALUES
    (1, 1, 3, 3, 2, 'Antonio se ha mareado y ha caído al suelo en el comedor.', '2025-01-15 11:00:00', CURRENT_TIMESTAMP);
-- 2. María (Agitada): Estado 'Activa' (ID 1)
INSERT INTO users_incidents (user_id, incident_user_id, significance_user_id, created_by_worker_id, incident_status_id, comment, created_at, updated_at)
VALUES
    (2, 2, 2, 2, 1, 'María está muy agitada y se niega a comer.', '2025-01-15 20:00:00', CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- INSERTAR COMENTARIOS EN INCIDENCIAS DE CENTRO
-- --------------------------------------------------------

INSERT INTO incidents_cd_comments (cd_incident_id, worker_id, comment, created_at, updated_at)
VALUES
    (1, 5, 'He cerrado la llave de paso general por si acaso.', '2025-01-14 09:05:00', CURRENT_TIMESTAMP),
    (1, 2, 'El fontanero confirma que llega en 20 minutos.', '2025-01-14 09:15:00', CURRENT_TIMESTAMP),
    (1, 1, 'Perfecto, gracias. Estaré en recepción.', '2025-01-14 09:30:00', CURRENT_TIMESTAMP),
    (2, 3, 'Voy a por el cubo y la fregona.', '2025-01-14 16:02:00', CURRENT_TIMESTAMP),
    (2, 5, 'Tranquila, ya lo he limpiado con papel.', '2025-01-14 16:05:00', CURRENT_TIMESTAMP),
    (2, 3, 'Ah vale, genial. Cierro aviso.', '2025-01-14 16:10:00', CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- INSERTAR COMENTARIOS EN INCIDENCIAS DE USUARIO
-- --------------------------------------------------------

INSERT INTO incidents_users_comments (user_incident_id, worker_id, comment, created_at, updated_at)
VALUES
    (1, 2, '¿Tiene alguna herida visible?', '2025-01-15 11:05:00', CURRENT_TIMESTAMP),
    (1, 3, 'No, revisado y sin sangre. Solo susto.', '2025-01-15 11:10:00', CURRENT_TIMESTAMP),
    (1, 1, 'Anotado en el libro de relevos.', '2025-01-15 11:45:00', CURRENT_TIMESTAMP),
    (2, 1, '¿Se ha tomado la pastilla?', '2025-01-15 20:10:00', CURRENT_TIMESTAMP),
    (2, 2, 'Sí, se la dí yo, pero tarda en hacer efecto.', '2025-01-15 20:20:00', CURRENT_TIMESTAMP),
    (2, 5, 'Le pondré música suave.', '2025-01-15 20:35:00', CURRENT_TIMESTAMP);


-- --------------------------------------------------------
-- TABLA DE ROLES
-- --------------------------------------------------------
INSERT INTO roles_enum (role_name, created_at, updated_at) VALUES
-- Jefatura
('ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ROLE_DIRECTOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ROLE_COORDINADOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Equipo Técnico
('ROLE_ENFERMERO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ROLE_FISIO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ROLE_TO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ROLE_TS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ROLE_PSICO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Atención Directa
('ROLE_TAS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Servicios
('ROLE_CONDUCTOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ROLE_COPILOTO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (role_name) DO NOTHING;

-- --------------------------------------------------------
-- TABLA DE POSICIONES
-- --------------------------------------------------------
INSERT INTO positions_enum (position_name, created_at, updated_at) VALUES
-- Dirección
('Director/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Recursos Humanos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Coordinador/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Administración
('Administrativo/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Recepcionista', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Equipo Tecnico
('Enfermero/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fisioterapeuta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Terapeuta Ocupacional', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Trabajador/a Social', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Psicólogo/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Atencion Directa
('Gerocultor/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Técnico/a de Atención Sociosanitaria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Servicios
('Conductor/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Copiloto', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Personal de Limpieza', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (position_name) DO NOTHING;

-- --------------------------------------------------------
-- TABLA DE WORKERS_ROLES
-- --------------------------------------------------------
INSERT INTO schema_template.workers_roles (worker_id, role_id) VALUES
-- Directora -> ADMIN / DIRECTOR
(1, 1),
(1, 2),
-- Coordinador / TAS
(2, 3),
(2, 9),
-- Administrativo -> Admin
(3, 1),
-- Enfermera
(5, 4),
-- RRHH -> ADMIN / TO
(6, 1),
(6, 6),
-- Recepción -> Coordinador
(7, 3),
-- Fisioterapeuta
(8, 5),
-- Terapeuta Ocupacional
(9, 6),
-- Trabajador Social
(10, 7),
-- Psicologo
(11, 8),
-- Gerocultora
(12, 9),
-- Tecnico Atencion Sociosanitaria
(13, 9),
-- Conductor / TAS
(14, 10),
(14, 9),
-- Copiloto / TAS
(15, 11),
(15, 9);

-- --------------------------------------------------------
-- TABLA DE WORKERS_POSITIONS
-- --------------------------------------------------------
INSERT INTO schema_template.workers_positions (worker_id, position_id) VALUES
(1, 1),  -- Ana -> Director/a
(2, 3),  -- Carlos -> Coordinador/a
(2, 12), -- Carlos -> TAS
(3, 4),  -- Lucía -> Administrativo/a
(5, 6),  -- Elena -> Enfermero/a
(6, 2),  -- Roberto -> Recursos Humanos
(6, 8),  -- Roberto -> Terapeuta Ocupacional
(7, 5),  -- Beatriz -> Recepcionista
(8, 7),  -- Laura -> Fisioterapeuta
(9, 8),  -- Pablo -> Terapeuta Ocupacional
(10, 9), -- Sara -> Trabajador/a Social
(11, 10),-- David -> Psicólogo/a
(12, 11),-- Carmen -> Gerocultor/a
(13, 12),-- Javier -> Técnico/a de Atención Sociosanitaria
(14, 13),-- Manuel -> Conductor/a
(15, 14),-- Julián -> Copiloto
(16, 15);-- Rosa -> Personal de Limpieza

-- --------------------------------------------------------
-- CATÁLOGO: REGIONES (Comunidades Autónomas)
-- --------------------------------------------------------
INSERT INTO regions_enum (region_name, created_at, updated_at) VALUES
('Comunidad de Madrid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Andalucía', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cataluña', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (region_name) DO NOTHING;

-- --------------------------------------------------------
-- CATÁLOGO: PROVINCIAS
-- --------------------------------------------------------
INSERT INTO provinces_enum (province_name, created_at, updated_at) VALUES
('Madrid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sevilla', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Barcelona', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (province_name) DO NOTHING;

-- --------------------------------------------------------
-- CATÁLOGO: CIUDADES / POBLACIONES
-- --------------------------------------------------------
INSERT INTO cities_enum (city_name, created_at, updated_at) VALUES
('Móstoles', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Alcalá de Henares', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sevilla Capital', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('L''Hospitalet', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATÁLOGO: RELACIONES FAMILIARES
-- --------------------------------------------------------
INSERT INTO user_relationships_enum (relationship_name, created_at, updated_at) VALUES
('Hijo/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cónyuge (Esposo/a)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Hermano/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Nieto/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sobrino/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Amigo/a / Vecino/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tutor Legal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (relationship_name) DO NOTHING;


-- --------------------------------------------------------
-- DIRECCIONES DE USUARIOS
-- --------------------------------------------------------
INSERT INTO user_adresses (user_id, address, postal_code, city_id, province_id, region_id, created_at, updated_at)
VALUES
    -- 1. Antonio (Vive en Móstoles, Madrid)
    (1, 'Calle de la Libertad, 45, 3ºA', '28931', 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 2. María (Vive en Alcalá, Madrid)
    (2, 'Av. Complutense, 12, Bajo B', '28801', 2, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 3. José (Vive en Sevilla, Andalucía)
    (3, 'Calle Betis, 8, 1º Izq', '41010', 3, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 4. Dolores (Vive en Móstoles también)
    (4, 'Plaza del Pradillo, 2', '28932', 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CONTACTOS DE USUARIOS
-- --------------------------------------------------------
INSERT INTO user_contacts (user_id, contact_name, contact_phone, contact_email, contact_relationship_id, is_contact_main, contact_note, created_at, updated_at)
VALUES
    -- Contactos de Antonio (ID 1)
    (1, 'Laura Martínez', '600111222', 'laura.mtz@email.com', 1, true, 'Tiene llaves del domicilio principal.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'Pedro Martínez', '600333444', 'pedro.mtz@email.com', 1, false, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Contactos de María (ID 2)
    (2, 'Juan López', '611222333', 'juan.lopez@email.com', 2, true, 'Suele estar en casa a partir de las 17:00h.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Contactos de José (ID 3)
    (3, 'Manuel Hernández', '622444555', 'manuel.hdez@email.com', 3, true, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Ana Hernández', '622555666', 'ana.hdez@email.com', 5, false, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Contactos de Dolores (ID 4)
    (4, 'Marta Gómez', '633777888', 'marta.gomez@email.com', 4, true, 'Contactar por WhatsApp si no responde.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- DÍAS DE ASISTENCIA DE USUARIOS
-- --------------------------------------------------------

INSERT INTO user_attendance_days (user_id, day_id, start_at, end_at, created_at, updated_at)
VALUES
-- 1. ANTONIO (ID 1): Viene TODOS los días (L-V) de 09:00 a 17:00
    (1, 1, '09:00:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 2, '09:00:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 3, '09:00:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 4, '09:00:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 5, '09:00:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- 2. MARÍA (ID 2): Lunes, Miércoles, Viernes (Horario partido ejemplo: 10 a 18)
    (2, 1, '10:00:00', '18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 3, '10:00:00', '18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 5, '10:00:00', '18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- 3. JOSÉ (ID 3): Martes y Jueves (Mañanas)
    (3, 2, '09:00:00', '14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 4, '09:00:00', '14:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- 4. DOLORES (ID 4): Lunes a Jueves (Jornada completa)
    (4, 1, '08:30:00', '17:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 2, '08:30:00', '17:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 3, '08:30:00', '17:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, '08:30:00', '17:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATALOGO: TURNOS DE RUTA (route_shift_enum)
-- --------------------------------------------------------
INSERT INTO route_shift_enum (route_name, created_at, updated_at)
VALUES
    ('MAÑANA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('TARDE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON CONFLICT (route_name) DO NOTHING;

-- --------------------------------------------------------
-- CATÁLOGO: VEHÍCULOS (route_vehicles)
-- --------------------------------------------------------
INSERT INTO route_vehicles (license_plate, capacity, has_wheelchair, wheelchair_capacity, is_active, created_at, updated_at)
VALUES
    ('1234-BBB', 12, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('5678-JWL', 8, true, 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON CONFLICT (license_plate) DO NOTHING;

-- --------------------------------------------------------
-- RUTAS DE TRANSPORTE (transport_routes)
-- --------------------------------------------------------
-- Limpiamos previo para evitar duplicados en pruebas
TRUNCATE TABLE transport_routes CASCADE;

INSERT INTO transport_routes (route_number, start_time, end_time, route_shift_id, route_vehicle_id, worker_driver_id, worker_copilot_id, is_active, created_at, updated_at)
VALUES
    -- RUTA 101: Mañana (Recogida) - Furgoneta Normal
    (101, '08:00:00', '09:30:00', 1, 1, 1, 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- RUTA 102: Tarde (Vuelta) - Furgoneta Adaptada
    (102, '17:00:00', '18:30:00', 2, 2, 2, 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- ASIGNACIÓN DE PASAJEROS (transport_routes_user)
-- --------------------------------------------------------
TRUNCATE TABLE transport_routes_user CASCADE;

INSERT INTO transport_routes_user (route_id, user_id) VALUES
    -- Ruta 101 (Mañana): Van Antonio (1) y María (2)
    (1, 1),
    (1, 2),
    -- Ruta 102 (Tarde): Vuelven los mismos
    (2, 1),
    (2, 2);

-- --------------------------------------------------------
-- CATALOGO: Tipos de Ropa
-- --------------------------------------------------------
INSERT INTO clothing_type_enum (clothes_name, created_at, updated_at)
VALUES
    ('Pantalón', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Camisa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Chaqueta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Ropa interior', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Chándal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATALOGO: Tipos de Objetos
-- --------------------------------------------------------
INSERT INTO objects_type_enum (object_name, created_at, updated_at)
VALUES
    ('Gafas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Bastón', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Oxígeno', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Neceser', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATALOGO: Condiciones
-- --------------------------------------------------------
INSERT INTO condition_type_enum (condition_name, created_at, updated_at)
VALUES
    ('Nuevo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Buen estado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Desgastado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATALOGO: Tamaño del pañal
-- --------------------------------------------------------
INSERT INTO diapers_size_enum (size, created_at, updated_at)
VALUES
    ('Pequeño', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Mediano', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Grande', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATALOGO: Tipo del pañal
-- --------------------------------------------------------
INSERT INTO diapers_type_enum (type, created_at, updated_at)
VALUES
    ('Elástico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Braga-pañal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Anatómico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATALOGO: Regreso cosas (return_reason_enum)
-- --------------------------------------------------------
INSERT INTO return_reason_enum (reason, created_at, updated_at) VALUES
('No devuelto (En uso)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Devuelto a familiar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Desechado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- TABLA: Ropa Física (UserClothes)
-- --------------------------------------------------------
INSERT INTO user_clothing (clothes_id, is_clean, is_returned, received_at, returned_at, return_reason_id, created_at, updated_at)
VALUES
    (3, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- TABLA: Objetos Físicos (UserObject)
-- --------------------------------------------------------
INSERT INTO user_objects (object_id, condition_id, comment, created_at, updated_at)
VALUES
    (1, 2, 'Funda roja', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 3, 'Madera, empuñadura gastada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 2, 'Oído derecho, marca Siemens', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 1, 'Concentrador portátil con batería extra', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- TABLA: Pañales Físicos (UserDiapers)
-- --------------------------------------------------------
INSERT INTO user_diapers (size_id, type_id, quantity, created_at, updated_at)
VALUES
    (2, 2, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 1, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 3, 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- PERTENENCIAS (Tabla Principal: user_belongings)
-- --------------------------------------------------------
INSERT INTO user_belongings
(user_id, user_clothing_id, user_object_id, user_diaper_id, worker_id, comment, is_request, created_at, updated_at)
VALUES
    (1, 1, 1, NULL, 12, 'Chaqueta de punto azul. Trae también su bastón.', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, 2, NULL, 5, 'Chaqueta de lana.', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, NULL, 1, 13, 'Pantalón manchado para lavandería y reposición de pañales.', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, 3, NULL, 7, 'Viene con chándal nuevo. Las gafas se quedan en taquilla.', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================================
-- 1. CATÁLOGOS MÉDICOS (Enums)
-- ============================================================

-- Nombres de Medicamentos
INSERT INTO medication_name_enum (medication_name, created_at, updated_at)
VALUES
    ('Sintrom (Acenocumarol)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),   -- ID 1
    ('Omeprazol', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),                -- ID 2
    ('Enalapril', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),                -- ID 3
    ('Insulina Lantus', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),          -- ID 4
    ('Paracetamol', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),              -- ID 5
    ('Adiro 100', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),                -- ID 6
    ('Seguril (Furosemida)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),     -- ID 7
    ('Ventolín', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),                 -- ID 8
    ('Amoxicilina', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),              -- ID 9
    ('Ebastina 10mg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);            -- ID 10

-- Vías de Administración
INSERT INTO medication_application_enum (medication_application_name, created_at, updated_at)
VALUES
    ('Oral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Subcutánea', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Intravenosa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Tópica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Inhalada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Condiciones de Conservación
INSERT INTO storage_condition_enum (storage_name, created_at, updated_at)
VALUES
    ('Temperatura Ambiente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Refrigerado (Nevera)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Proteger de la luz', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Enfermedades / Patologías
INSERT INTO illness_enum (illness_name, created_at, updated_at)
VALUES
    ('Hipertensión Arterial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Diabetes Tipo 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Alzheimer (Fase Inicial)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Artrosis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Insuficiencia Cardíaca', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('EPOC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Alergias
INSERT INTO allergies_enum (allergy_name, created_at, updated_at)
VALUES
    ('Penicilina', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),           -- ID 1
    ('AINEs (Ibuprofeno/Aspirina)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ID 2
    ('Lactosa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),              -- ID 3
    ('Sulfamidas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),           -- ID 4
    ('Polen / Gramíneas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);    -- ID 5

-- ============================================================
-- 2. INVENTARIO DE MEDICAMENTOS (Medications)
-- ============================================================

INSERT INTO medications (medication_name_id, dose, reception_date, expiration_date, storage_condition_id, medication_application_id, created_at, updated_at)
VALUES
    -- 1. Sintrom
    (1, '4mg (Según pauta)', '2025-01-01', '2026-06-01', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 2. Omeprazol
    (2, '20mg', '2025-01-01', '2027-01-01', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 3. Enalapril
    (3, '20mg', '2025-01-05', '2026-12-01', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 4. Insulina Lantus
    (4, 'Pluma precargada', '2025-01-10', '2025-06-10', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 5. Paracetamol
    (5, '1g', '2025-01-01', '2028-01-01', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 6. Amoxicilina
    (9, '500mg', '2025-01-01', '2026-01-01', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 7. Ebastina (Para la alergia) -> Medication Name ID 10
    (10, '10mg Desayuno', '2025-02-01', '2026-02-01', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================================
-- 3. HISTORIAL MÉDICO DE USUARIOS (user_medical_info)
-- ============================================================

INSERT INTO user_medical_info (user_id, worker_id, is_active, created_at, updated_at)
VALUES
    (1, 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Antonio
    (2, 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- María
    (3, 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- José
    (4, 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Dolores

-- ============================================================
-- 4. REGISTROS CLÍNICOS (Enfermedades y Alergias)
-- ============================================================

-- Enfermedades
INSERT INTO user_illnesses (user_medical_info_id, illness_id, created_at, updated_at) VALUES (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Antonio (Hipertensión)
INSERT INTO user_illnesses (user_medical_info_id, illness_id, created_at, updated_at) VALUES (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- María (Diabetes)
INSERT INTO user_illnesses (user_medical_info_id, illness_id, created_at, updated_at) VALUES (2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- María (Alzheimer)
INSERT INTO user_illnesses (user_medical_info_id, illness_id, created_at, updated_at) VALUES (3, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- José (Artrosis)

-- Alergias (¡Ojo al orden para los IDs!)
-- 1. José -> Penicilina (Generará ID 1)
INSERT INTO user_allergies (user_medical_info_id, allergy_id, comment, created_at, updated_at)
VALUES (3, 1, 'Reacción cutánea grave.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 2. Dolores -> Lactosa (Generará ID 2)
INSERT INTO user_allergies (user_medical_info_id, allergy_id, comment, created_at, updated_at)
VALUES (4, 3, 'Intolerancia digestiva severa.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 3. Antonio -> Polen (Generará ID 3)
INSERT INTO user_allergies (user_medical_info_id, allergy_id, comment, created_at, updated_at)
VALUES (1, 5, 'Rinitis estacional fuerte en primavera.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================================
-- 5. TRATAMIENTOS (treatment_details)
-- ============================================================

INSERT INTO treatment_details (start_date, end_date, is_active, comment, created_at, updated_at)
VALUES
    ('2024-01-01', NULL, true, 'Control tensión.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ID 1 (Antonio)
    ('2024-06-15', NULL, true, 'Control diabetes.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ID 2 (María)
    ('2025-01-01', '2025-02-01', true, 'Dolor puntual.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- ID 3 (José)

-- ============================================================
-- 6. RELACIONES MANY-TO-MANY
-- ============================================================

-- A. Enfermedad <-> Tratamiento
INSERT INTO user_illness_treatment (user_illness_id, treatment_detail_id) VALUES (1, 1);
INSERT INTO user_illness_treatment (user_illness_id, treatment_detail_id) VALUES (2, 2);
INSERT INTO user_illness_treatment (user_illness_id, treatment_detail_id) VALUES (4, 3);

-- B. Tratamiento <-> Medicamento
INSERT INTO treatment_details_medication (treatment_detail_id, medication_id) VALUES (1, 3); -- Enalapril
INSERT INTO treatment_details_medication (treatment_detail_id, medication_id) VALUES (2, 4); -- Insulina
INSERT INTO treatment_details_medication (treatment_detail_id, medication_id) VALUES (3, 5); -- Paracetamol

-- C. Alergia <-> Medicamento para tratarla (CORREGIDO)
-- Lógica: "Si tienes esta alergia, debes tomar este medicamento"

-- CASO CORRECTO: Antonio (UserAllergy ID 3 - Polen) toma Ebastina (Medication ID 7)
INSERT INTO allergies_medications (user_allergy_id, medication_id) VALUES (3, 7);

-- ============================================================
-- TAREAS DE HIGIENE (bathroom_tasks)
-- ============================================================

INSERT INTO bathroom_tasks (task_name, estimated_time, created_at, updated_at)
VALUES
    ('Aseo en Lavabo (Cara/Manos)', '00:10:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Cambio de Pañal / Absorbente', '00:08:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Acompañamiento WC', '00:05:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Higiene Bucal (Post-comida)', '00:05:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================================
-- TURNOS DE BAÑO E HIGIENE (bathroom_turns)
-- ============================================================
INSERT INTO bathroom_turns (turn_name, start_at, end_at, created_at, updated_at)
VALUES
    ('Ronda Llegada', '09:00:00', '10:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Ronda Pre-Comida', '12:30:00', '13:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Higiene Post-Siesta', '15:30:00', '16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Preparación Salida', '16:30:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================================
-- HORARIOS DE USO DE BAÑO(bathroom_schedule)
-- ============================================================
INSERT INTO bathroom_schedule (user_id, bathroom_turn_id, bathroom_task_id, created_at, updated_at)
VALUES
    -- 1. ANTONIO (ID 1): Al llegar, aseo general en lavabo (ID 1).
    (1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 2. MARÍA (ID 2): Antes de comer, ir al WC (ID 3).
    (2, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 3. JOSÉ (ID 3): Cambio de pañal tras la siesta (ID 2).
    (3, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 4. DOLORES (ID 4): Aseo de cara/manos antes de irse (ID 1).
    (4, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 5. ANTONIO (ID 1): Higiene bucal después de comer (ID 4).
    (1, 2, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);