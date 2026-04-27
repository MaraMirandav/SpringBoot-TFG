-- ═══════════════════════════════════════════════════════════════════════
-- V2__seed_tenant_data.sql — Catálogos base para cada tenant
--
-- IMPORTANTE: Usar ON CONFLICT DO NOTHING para permitir que la migración
-- sea idempotente (pueda ejecutarse múltiples veces sin errores).
-- NOTA: Las tablas provinces_enum y cities_enum NO tienen FK a regions_enum
-- en los modelos Java - la relación jerárquica se maneja a nivel aplicación.
-- ═══════════════════════════════════════════════════════════════════════

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- ROLES (Definición de roles de trabajadores)
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO roles_enum (role_name, created_at, updated_at, created_by, updated_by) VALUES
    ('ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_DIRECTOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_COORDINADOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_ENFERMERO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_FISIO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_TO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_TS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_PSICO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_TAS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_CONDUCTOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('ROLE_COPILOTO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (role_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- SEXO
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO sex_enum (sex, created_at, updated_at, created_by, updated_by) VALUES
    ('Hombre', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Mujer', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (sex) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- DEPENDENCIA (Nivel de dependencia del usuario)
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO user_dependency_enum (dependency_level, created_at, updated_at, created_by, updated_by) VALUES
    ('Sin Dependencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Baja', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Media', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Alta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (dependency_level) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- RELACIONES FAMILIARES
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO user_relationships_enum (relationship_name, created_at, updated_at, created_by, updated_by) VALUES
    ('Hijo/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cónyuge', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Hermano/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Nieto/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Sobrino/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Amigo/a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Tutor Legal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (relationship_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- DÍAS ABIERTOS (Días laborables del centro)
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO open_days (day_name, open_at, close_at, created_at, updated_at, created_by, updated_by) VALUES
    ('Lunes', '08:15:00', '18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Martes', '08:15:00', '18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Miércoles', '08:15:00', '18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Jueves', '08:15:00', '18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Viernes', '08:15:00', '18:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (day_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- ESTADOS DE INCIDENCIAS
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO incident_status_enum (status_name, created_at, updated_at, created_by, updated_by) VALUES
    ('Activa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('En revisión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cerrada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cancelada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (status_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- TIPOS DE INCIDENCIAS DE CENTRO
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO incident_cd_enum (incident_name, created_at, updated_at, created_by, updated_by) VALUES
    ('Mantenimiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Limpieza', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Suministros', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (incident_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- NIVELES DE GRAVEDAD DE CENTRO
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO significances_cd_enum (significance_name, created_at, updated_at, created_by, updated_by) VALUES
    ('Baja', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Media', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Alta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (significance_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- TIPOS DE INCIDENCIAS DE USUARIO
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO incidents_user_enum (incident_name, created_at, updated_at, created_by, updated_by) VALUES
    ('Salud / Caída', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Comportamiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Medicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (incident_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- NIVELES DE GRAVEDAD DE USUARIO
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO significances_user_enum (significance_name, created_at, updated_at, created_by, updated_by) VALUES
    ('Leve', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Moderada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Crítica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (significance_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- TURNOS DE RUTA
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO routes_shift_enum (route_name, created_at, updated_at, created_by, updated_by) VALUES
    ('MAÑANA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('TARDE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (route_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- REGIONES (Comunidades Autónomas)
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO regions_enum (region_name, created_at, updated_at, created_by, updated_by) VALUES
    ('Andalucía', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Aragón', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Asturias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Baleares', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Canarias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cantabria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Castilla-La Mancha', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Castilla y León', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cataluña', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Extremadura', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Galicia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('La Rioja', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Comunidad de Madrid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Navarra', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('País Vasco', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('C. Valenciana', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (region_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- PROVINCIAS (SIN region_id - los modelos Java no tienen esta FK)
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO provinces_enum (province_name, created_at, updated_at, created_by, updated_by) VALUES
    ('Almería', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cádiz', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Córdoba', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Granada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Huelva', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Jaén', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Málaga', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Sevilla', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Huesca', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Teruel', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Zaragoza', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Asturias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Islas Baleares', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Las Palmas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Santa Cruz de Tenerife', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cantabria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Albacete', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Ciudad Real', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cuenca', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Guadalajara', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Toledo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Ávila', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Burgos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('León', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Palencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Salamanca', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Segovia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Soria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Valladolid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Zamora', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Barcelona', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Girona', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Lleida', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Tarragona', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Badajoz', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cáceres', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('A Coruña', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Lugo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Ourense', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Pontevedra', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('La Rioja', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Madrid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Navarra', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Álava', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Bilbao', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Gipuzkoa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Alicante', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Castellón', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Valencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (province_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- CIUDADES / POBLACIONES (SIN province_id - los modelos Java no tienen esta FK)
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO cities_enum (city_name, created_at, updated_at, created_by, updated_by) VALUES
    ('Sevilla Capital', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Barcelona Capital', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Madrid Capital', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Alcalá de Henares', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Móstoles', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Fuenlabrada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Getafe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Leganés', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Parla', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Alcorcón', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Torrejón de Ardoz', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (city_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- TIPOS DE ROPA
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO clothing_types_enum (clothing_name, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Pantalón', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Camisa', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Chaqueta', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Ropa interior', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Chándal', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Calcetines', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Zapatos', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (clothing_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- TIPOS DE OBJETOS
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO object_types_enum (object_name, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Gafas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Bastón', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Oxígeno', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Neceser', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Audífono', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Móvil', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cartera', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (object_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- CONDICIONES DE OBJETOS
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO item_conditions_enum (condition_name, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Nuevo', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Bueno', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Regular', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Desgastado', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (condition_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- TAMAÑOS DE PAÑAL
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO diaper_sizes_enum (size, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('XS', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('S', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('M', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('L', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('XL', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('XXL', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (size) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- TIPOS DE PAÑAL
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO diaper_types_enum (type, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Elástico', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Braga-pañol', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Anatómico', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Con pestaña', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (type) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- RAZONES DE DEVOLUCIÓN
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO return_reasons_enum (reason, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('En uso', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Devuelto a familiar', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Desechado', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Perdido', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (reason) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- NOMBRES DE MEDICAMENTOS
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO medication_names_enum (medication_name, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Acenocumarol (Sintrom)', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Omeprazol', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Enalapril', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Insulina Lantus', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Paracetamol', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Adiro 100', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Furosemida (Seguril)', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Ventolín', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Amoxicilina', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Ebastina 10mg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Metoprolol', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Atorvastatina', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Levotiroxina', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Mirtazapina', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Tramadol', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (medication_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- VÍAS DE ADMINISTRACIÓN
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO medication_applications_enum (medication_application_name, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Oral', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Subcutánea', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Intravenosa', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Tópica', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Inhalada', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Rectal', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Transdérmica', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (medication_application_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- CONDICIONES DE CONSERVACIÓN
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO storage_conditions_enum (storage_name, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Temperatura Ambiente', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Refrigerado', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Congelado', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Proteger de la luz', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Proteger de humedad', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (storage_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- ENFERMEDADES / PATOLOGÍAS
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO illnesses_enum (illness_name, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Hipertensión Arterial', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Diabetes Tipo 1', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Diabetes Tipo 2', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Demencia / Alzheimer', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Artrosis', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Insuficiencia Cardíaca', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('EPOC', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Ictus', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Parkinson', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Osteoporosis', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Insuficiencia Renal', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Artritis Reumatoide', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (illness_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- ALERGIAS
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO allergies_enum (allergy_name, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Penicilina', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('AINEs', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Lactosa', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Sulfamidas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Polen / Gramíneas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Ácaros', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Picadura de avispa', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Látex', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Huevo', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Marisco', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (allergy_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- TAREAS DE HIGIENE
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO bathroom_tasks_enum (task_name, estimated_time, is_active, created_at, updated_at, created_by, updated_by) VALUES
    ('Aseo en Lavabo', '00:10:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cambio de Pañal', '00:08:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Acompañamiento WC', '00:05:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Higiene Bucal', '00:05:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Cambio de ropa', '00:10:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Corte de uñas', '00:10:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Peinado', '00:05:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
    ('Afeitado', '00:15:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system')
ON CONFLICT (task_name) DO NOTHING;

-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-- TURNOS DE BAÑO
-- ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INSERT INTO bathroom_turns (turn_name, start_at, end_at, created_at, updated_at) VALUES
    ('Ronda Llegada', '09:00:00', '10:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Ronda Pre-Comida', '12:30:00', '13:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Higiene Post-Siesta', '15:30:00', '16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Preparación Salida', '16:30:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (turn_name) DO NOTHING;