-- ═══════════════════════════════════════════════════════════════════════
-- V3__add_audit_columns.sql — Añadir columnas de auditoría a tenants existentes
--
-- CONTEXTO: BaseEntity usa AuditingListener (JPA @EntityListeners) para
-- rellenar automáticamente created_by y updated_by en cada INSERT/UPDATE.
-- Sin estas columnas, Hibernate lanza un error al intentar escribir en
-- tablas que no tienen esos campos.
--
-- ESTRATEGIA: IF NOT EXISTS para que la migración sea idempotente
-- (re-ejecutable sin error si las columnas ya existen).
--
-- DEFAULT 'system': cubre las filas históricas que ya existen en el tenant.
-- Para filas nuevas, AuditingListener sobreescribirá 'system' con el
-- usuario real (extraído del JWT por AuditingContextHolder).
--
-- TABLAS AFECTADAS (22 en total):
--   Las tablas de catálogo base (roles_enum, sex_enum, etc.) ya tenían
--   estas columnas desde V1. Las tablas de entidades de negocio (users,
--   workers, etc.) y algunas secundarias no las tenían.
--   Las tablas junction puras (workers_roles, transport_routes_user, etc.)
--   solo tienen created_at y no necesitan auditing completo.
-- ═══════════════════════════════════════════════════════════════════════

-- ── 1. bathroom_turns ──────────────────────────────────────────────────
-- Tabla de turnos de higiene — no tenía auditing porque se creó como
-- tabla de referencia pero luego se usó como entidad de negocio editable.
ALTER TABLE bathroom_turns
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 2. users ───────────────────────────────────────────────────────────
-- Usuarios (residentes del centro de día). El AuditingListener necesita
-- saber quién dio de alta o modificó a cada residente.
ALTER TABLE users
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 3. route_vehicles ──────────────────────────────────────────────────
-- Vehículos de transporte del centro.
ALTER TABLE route_vehicles
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 4. user_contacts ───────────────────────────────────────────────────
-- Contactos / familiares del usuario.
ALTER TABLE user_contacts
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 5. user_addresses ──────────────────────────────────────────────────
-- Dirección postal del usuario.
ALTER TABLE user_addresses
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 6. user_attendance_days ────────────────────────────────────────────
-- Días de asistencia acordados para cada usuario.
ALTER TABLE user_attendance_days
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 7. user_attendance_records ─────────────────────────────────────────
-- Registro diario real de asistencia (presencia/ausencia).
ALTER TABLE user_attendance_records
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 8. transport_routes ────────────────────────────────────────────────
-- Rutas de transporte con conductor y vehículo asignado.
ALTER TABLE transport_routes
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 9. cd_incidents ────────────────────────────────────────────────────
-- Incidencias del centro (mantenimiento, limpieza, suministros).
ALTER TABLE cd_incidents
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 10. incidents_cd_comments ──────────────────────────────────────────
-- Comentarios de seguimiento en incidencias de centro.
ALTER TABLE incidents_cd_comments
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 11. users_incidents ────────────────────────────────────────────────
-- Incidencias asociadas a un usuario (salud, comportamiento, medicación).
ALTER TABLE users_incidents
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 12. incidents_users_comments ───────────────────────────────────────
-- Comentarios de seguimiento en incidencias de usuarios.
ALTER TABLE incidents_users_comments
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 13. bathroom_schedules ─────────────────────────────────────────────
-- Planificación de higiene por usuario, turno y tarea.
ALTER TABLE bathroom_schedules
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 14. user_clothings ─────────────────────────────────────────────────
-- Prendas de ropa del usuario que gestiona el centro.
ALTER TABLE user_clothings
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 15. user_objects ───────────────────────────────────────────────────
-- Objetos personales del usuario (gafas, bastón, audífonos...).
ALTER TABLE user_objects
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 16. user_diapers ───────────────────────────────────────────────────
-- Stock de pañales del usuario en el centro.
ALTER TABLE user_diapers
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 17. user_belongings ────────────────────────────────────────────────
-- Pertenencias generales del usuario (ropa + objetos + pañales).
ALTER TABLE user_belongings
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 18. user_medical_info ──────────────────────────────────────────────
-- Información médica general del usuario (cabecera del historial).
ALTER TABLE user_medical_info
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 19. user_allergies ─────────────────────────────────────────────────
-- Alergias conocidas del usuario.
ALTER TABLE user_allergies
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 20. user_illnesses ─────────────────────────────────────────────────
-- Patologías / enfermedades diagnosticadas del usuario.
ALTER TABLE user_illnesses
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 21. medications ────────────────────────────────────────────────────
-- Medicamentos concretos (nombre, vía, dosis, fechas) que usa el centro.
ALTER TABLE medications
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';

-- ── 22. treatment_details ──────────────────────────────────────────────
-- Detalles de tratamiento (fechas de inicio/fin, observaciones).
ALTER TABLE treatment_details
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    ADD COLUMN IF NOT EXISTS updated_by VARCHAR(50) NOT NULL DEFAULT 'system';
