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

SELECT * FROM users;

SELECT
    u.id,
    u.first_name AS "Nombre",
    u.alias AS "Alias",
    s.sex AS "Sexo",
    d.nivel_dependency AS "Nivel Dependencia"
FROM schema_template.users u
JOIN schema_template.sex_enum s ON u.sex_id = s.id
JOIN schema_template.user_dependency_enum d ON u.dependency_id = d.id;