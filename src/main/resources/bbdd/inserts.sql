-- --------------------------------------------------------
-- TABLA DE TRABAJADORES (WORKERS)
-- Tabla: schema_template.workers
-- --------------------------------------------------------
INSERT INTO schema_template.workers
(first_name, second_name, first_surname, second_surname, email, password, main_phone, second_phone, is_active, created_at, updated_at)
VALUES
    -- 1. Trabajadora con todos los datos completos
    ('Ana', 'María', 'García', 'López', 'ana.garcia@email.com', 'contraseña123', '600123456', '911223344', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 2. Trabajador sin segundo nombre ni segundo teléfono (campos NULL)
    ('Carlos', NULL, 'Rodríguez', 'Sánchez', 'carlos.rod@email.com', 'claveSegura2025', '655987654', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 3. Trabajadora sin segundo apellido
    ('Lucía', 'Carmen', 'Fernández', NULL, 'lucia.fer@email.com', 'admin1234', '611222333', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 4. Trabajador inactivo (is_active = false)
    ('Miguel', NULL, 'Torres', 'Ruiz', 'miguel.torres@email.com', 'passMiguel', '699888777', '933445566', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 5. Trabajadora estándar
    ('Elena', NULL, 'Moreno', 'Díaz', 'elena.moreno@email.com', 'elena2025', '622333444', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
    (first_name, second_name, first_surname, second_surname, alias, dni, birth_date, is_active, sex_id, dependency_id, created_at, updated_at)
VALUES
    -- 1. Antonio: Hombre (1), Dependencia Baja (1)
    ('Antonio', 'Luis', 'Martínez', 'Soria', 'Toño', '11223344B', '1950-03-15', true, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 2. María: Mujer (2), Dependencia Alta (3)
    ('María', 'Carmen', 'López', 'Vega', 'Mari', '55667788C', '1948-11-20', true, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 3. José: Hombre (1), Dependencia Media (2)
    ('José', NULL, 'Hernández', 'Ruiz', NULL, '99887766D', '1960-01-30', true, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- 4. Dolores: Mujer (2), Sin Dependencia (4)
    ('Dolores', NULL, 'Gómez', 'Pérez', 'Lola', '22334455E', '1955-07-12', false, 2, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- TABLA DE OPEN_DAYS
-- Tabla: schema_template.open_days
-- --------------------------------------------------------

INSERT INTO open_days (day, open_at, close_at, created_at, updated_at)
VALUES
    ('Lunes', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Martes', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Miércoles', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Jueves', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Viernes', '08:15', '18:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- --------------------------------------------------------
-- TABLA DE WORKERS_SCHEDULE
-- Tabla: schema_template.workers_schedules
-- --------------------------------------------------------
INSERT INTO workers_schedules (worker_id, day_id, start_at, end_at, created_at, updated_at)
VALUES
    -- Ana Gar  cía (ID 1)
    (1, 1, '2025-01-06 08:15:00', '2025-01-06 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 2, '2025-01-07 08:15:00', '2025-01-07 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 3, '2025-01-08 08:15:00', '2025-01-08 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 4, '2025-01-09 08:15:00', '2025-01-09 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 5, '2025-01-10 08:15:00', '2025-01-10 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Carlos Rodríguez (ID 2)
    (2, 1, '2025-01-06 08:15:00', '2025-01-06 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, '2025-01-07 08:15:00', '2025-01-07 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 3, '2025-01-08 08:15:00', '2025-01-08 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 4, '2025-01-09 08:15:00', '2025-01-09 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 5, '2025-01-10 08:15:00', '2025-01-10 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Lucía Fernández (ID 3) – Turno partido
    -- Mañana
    (3, 1, '2025-01-06 08:15:00', '2025-01-06 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 2, '2025-01-07 08:15:00', '2025-01-07 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, '2025-01-08 08:15:00', '2025-01-08 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 4, '2025-01-09 08:15:00', '2025-01-09 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 5, '2025-01-10 08:15:00', '2025-01-10 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Tarde
    (3, 1, '2025-01-06 15:00:00', '2025-01-06 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 2, '2025-01-07 15:00:00', '2025-01-07 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, '2025-01-08 15:00:00', '2025-01-08 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 4, '2025-01-09 15:00:00', '2025-01-09 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 5, '2025-01-10 15:00:00', '2025-01-10 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Miguel Torres (ID 4) – Horario en ruta
    -- Mañana
    (4, 1, '2025-01-06 07:50:00', '2025-01-06 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 2, '2025-01-07 07:50:00', '2025-01-07 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 3, '2025-01-08 07:50:00', '2025-01-08 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, '2025-01-09 07:50:00', '2025-01-09 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 5, '2025-01-10 07:50:00', '2025-01-10 11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Tarde
    (4, 1, '2025-01-06 16:00:00', '2025-01-06 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 2, '2025-01-07 16:00:00', '2025-01-07 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 3, '2025-01-08 16:00:00', '2025-01-08 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, '2025-01-09 16:00:00', '2025-01-09 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 5, '2025-01-10 16:00:00', '2025-01-10 19:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Elena Moreno (ID 5)
    (5, 1, '2025-01-06 08:15:00', '2025-01-06 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 2, '2025-01-07 08:15:00', '2025-01-07 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 3, '2025-01-08 08:15:00', '2025-01-08 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 4, '2025-01-09 08:15:00', '2025-01-09 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 5, '2025-01-10 08:15:00', '2025-01-10 18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- --------------------------------------------------------
-- TABLA DE WORKERS_SCHEDULES_RECORDS
-- Tabla: schema_template.workers_schedules_records
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
    -- Viernes (solo mañana, abierto)
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
    -- Viernes (solo mañana, abierto)
    (4, 25, '2025-01-17 07:53', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ELENA MORENO (ID 5)
INSERT INTO workers_schedules_records (worker_id, schedule_id, clock_in, clock_out, is_active, created_at, updated_at)
VALUES
    (5, 31, '2025-01-13 08:18', '2025-01-13 18:27', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 32, '2025-01-14 08:13', '2025-01-14 18:34', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 33, '2025-01-15 08:21', '2025-01-15 18:29', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 34, '2025-01-16 08:16', '2025-01-16 18:33', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Viernes (solo mañana, abierto)
    (5, 35, '2025-01-17 08:14', NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATÁLOGOS DE CENTRO (Tipos y Gravedad)
-- --------------------------------------------------------
-- Tipos de Incidencia de CENTRO
INSERT INTO schema_template.incident_cd_enum (incident_name, created_at, updated_at) VALUES
    ('Mantenimiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Limpieza', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Suministros', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Gravedad de CENTRO
INSERT INTO schema_template.significance_cd_enum (significance_name, created_at, updated_at) VALUES
    ('Baja', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Media', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Alta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- CATÁLOGOS DE USUARIO (Tipos y Gravedad)
-- --------------------------------------------------------
-- Tipos de Incidencia de USUARIO
INSERT INTO schema_template.incident_user_enum (incident_name, created_at, updated_at) VALUES
    ('Salud / Caída', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Comportamiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Medicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Gravedad de USUARIO
INSERT INTO schema_template.significance_user_enum (significance_name, created_at, updated_at) VALUES
    ('Leve', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Moderada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Crítica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- --------------------------------------------------------
-- INCIDENCIAS DE CENTRO (cd_incidents)
-- --------------------------------------------------------
INSERT INTO schema_template.cd_incidents (incident_cd_id, significance_cd_id, created_by_worker_id,comment, created_at, updated_at, is_active)
VALUES
    (1, 3, 1, 'La caldera hace un ruido extraño y pierde agua.', '2025-01-14 09:00:00', CURRENT_TIMESTAMP, true);

INSERT INTO schema_template.cd_incidents (incident_cd_id, significance_cd_id, created_by_worker_id,comment, created_at, updated_at, is_active)
VALUES
    (2, 1, 5, 'Se ha derramado café en la sala de estar.', '2025-01-14 16:00:00', CURRENT_TIMESTAMP, true);

-- --------------------------------------------------------
-- INCIDENCIAS DE USUARIO (users_incidents)
-- --------------------------------------------------------
INSERT INTO schema_template.users_incidents (user_id, incident_user_id, significance_user_id, created_by_worker_id, comment, created_at, updated_at, is_active)
VALUES
    (1, 1, 3, 3, 'Antonio se ha mareado y ha caído al suelo en el comedor.', '2025-01-15 11:00:00', CURRENT_TIMESTAMP, true);

INSERT INTO schema_template.users_incidents (user_id, incident_user_id, significance_user_id, created_by_worker_id, comment, created_at, updated_at, is_active)
VALUES
    (2, 2, 2, 2, 'María está muy agitada y se niega a comer.', '2025-01-15 20:00:00', CURRENT_TIMESTAMP, true);

-- --------------------------------------------------------
-- INSERTAR COMENTARIOS EN INCIDENCIAS DE CENTRO
-- --------------------------------------------------------

INSERT INTO schema_template.incidents_cd_comments (cd_incident_id, worker_id, comment, created_at, updated_at)
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

INSERT INTO schema_template.incidents_users_comments (user_incident_id, worker_id, comment, created_at, updated_at)
VALUES
    (1, 2, '¿Tiene alguna herida visible?', '2025-01-15 11:05:00', CURRENT_TIMESTAMP),
    (1, 3, 'No, revisado y sin sangre. Solo susto.', '2025-01-15 11:10:00', CURRENT_TIMESTAMP),
    (1, 1, 'Anotado en el libro de relevos.', '2025-01-15 11:45:00', CURRENT_TIMESTAMP),
    (2, 1, '¿Se ha tomado la pastilla?', '2025-01-15 20:10:00', CURRENT_TIMESTAMP),
    (2, 2, 'Sí, se la dí yo, pero tarda en hacer efecto.', '2025-01-15 20:20:00', CURRENT_TIMESTAMP),
    (2, 5, 'Le pondré música suave.', '2025-01-15 20:35:00', CURRENT_TIMESTAMP);