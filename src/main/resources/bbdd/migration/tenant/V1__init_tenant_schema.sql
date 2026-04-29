-- ═══════════════════════════════════════════════════════════════════════
-- V1__init_tenant_schema.sql — Schema inicial para cada tenant
--
-- ORDEN DE CREACIÓN (mínimo obligatorio por dependencias FK):
--   Nivel 1: Catálogos base sin FK
--   Nivel 2: Catálogos dinámicos sin FK
--   Nivel 3: Entidades que dependen de catálogos
--   Nivel 4: Entidades que dependen de otras entidades
--   Índices al final
-- ═══════════════════════════════════════════════════════════════════════

-- ═══════════════════════════════════════════════════════════════════════
-- NIVEL 1: CATÁLOGOS BASE (SIN FK - pueden crearse primero)
-- ═══════════════════════════════════════════════════════════════════════

-- roles_enum → reference from: workers_roles
CREATE TABLE roles_enum (
    id          SERIAL PRIMARY KEY,
    role_name   VARCHAR(50) NOT NULL UNIQUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- sex_enum → reference from: users
CREATE TABLE sex_enum (
    id          SERIAL PRIMARY KEY,
    sex         VARCHAR(20) NOT NULL UNIQUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_dependency_enum → reference from: users
CREATE TABLE user_dependency_enum (
    id               SERIAL PRIMARY KEY,
    dependency_level VARCHAR(50) NOT NULL UNIQUE,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by       VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_relationships_enum → reference from: user_contacts
CREATE TABLE user_relationships_enum (
    id                SERIAL PRIMARY KEY,
    relationship_name VARCHAR(50) NOT NULL UNIQUE,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by        VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by        VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- incident_status_enum → reference from: cd_incidents, users_incidents
CREATE TABLE incident_status_enum (
    id          SERIAL PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL UNIQUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- routes_shift_enum → reference from: transport_routes
CREATE TABLE routes_shift_enum (
    id          SERIAL PRIMARY KEY,
    route_name  VARCHAR(20) NOT NULL UNIQUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- incidents_user_enum → reference from: users_incidents
CREATE TABLE incidents_user_enum (
    id            SERIAL PRIMARY KEY,
    incident_name VARCHAR(50) NOT NULL UNIQUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- significances_user_enum → reference from: users_incidents
CREATE TABLE significances_user_enum (
    id                SERIAL PRIMARY KEY,
    significance_name VARCHAR(50) NOT NULL UNIQUE,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by        VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by        VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- incident_cd_enum → reference from: cd_incidents
CREATE TABLE incident_cd_enum (
    id            SERIAL PRIMARY KEY,
    incident_name VARCHAR(50) NOT NULL UNIQUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- significances_cd_enum → reference from: cd_incidents
CREATE TABLE significances_cd_enum (
    id                SERIAL PRIMARY KEY,
    significance_name VARCHAR(50) NOT NULL UNIQUE,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by        VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by        VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- open_days → reference from: user_attendance_days, workers_schedules
CREATE TABLE open_days (
    id          SERIAL PRIMARY KEY,
    day_name    VARCHAR(20) NOT NULL UNIQUE,
    open_at     TIME NOT NULL,
    close_at    TIME NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- regions_enum → reference from: user_addresses
CREATE TABLE regions_enum (
    id          SERIAL PRIMARY KEY,
    region_name VARCHAR(100) NOT NULL UNIQUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- provinces_enum → reference from: user_addresses
CREATE TABLE provinces_enum (
    id            SERIAL PRIMARY KEY,
    province_name VARCHAR(100) NOT NULL UNIQUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- cities_enum → reference from: user_addresses
CREATE TABLE cities_enum (
    id           SERIAL PRIMARY KEY,
    city_name    VARCHAR(100) NOT NULL UNIQUE,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by   VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by   VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- ═══════════════════════════════════════════════════════════════════════
-- NIVEL 2: CATÁLOGOS DINÁMICOS (SIN FK)
-- ═══════════════════════════════════════════════════════════════════════

-- allergies_enum → reference from: user_allergies
CREATE TABLE allergies_enum (
    id          SERIAL PRIMARY KEY,
    allergy_name VARCHAR(100) NOT NULL UNIQUE,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- illnesses_enum → reference from: user_illnesses
CREATE TABLE illnesses_enum (
    id          SERIAL PRIMARY KEY,
    illness_name VARCHAR(100) NOT NULL UNIQUE,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- medication_names_enum → reference from: medications
CREATE TABLE medication_names_enum (
    id               SERIAL PRIMARY KEY,
    medication_name  VARCHAR(100) NOT NULL UNIQUE,
    is_active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by       VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- medication_applications_enum → reference from: medications
CREATE TABLE medication_applications_enum (
    id                        SERIAL PRIMARY KEY,
    medication_application_name VARCHAR(100) NOT NULL UNIQUE,
    is_active                  BOOLEAN NOT NULL DEFAULT TRUE,
    created_at                 TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                 TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by                 VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by                 VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- storage_conditions_enum → reference from: medications
CREATE TABLE storage_conditions_enum (
    id            SERIAL PRIMARY KEY,
    storage_name  VARCHAR(100) NOT NULL UNIQUE,
    is_active     BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- bathroom_tasks_enum → reference from: bathroom_schedules
CREATE TABLE bathroom_tasks_enum (
    id            SERIAL PRIMARY KEY,
    task_name     VARCHAR(50) NOT NULL UNIQUE,
    estimated_time TIME NOT NULL,
    is_active     BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- diaper_sizes_enum → reference from: user_diapers
CREATE TABLE diaper_sizes_enum (
    id          SERIAL PRIMARY KEY,
    size         VARCHAR(20) NOT NULL UNIQUE,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- clothing_types_enum → reference from: user_clothings
CREATE TABLE clothing_types_enum (
    id            SERIAL PRIMARY KEY,
    clothing_name VARCHAR(50) NOT NULL UNIQUE,
    is_active     BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- diaper_types_enum → reference from: user_diapers
CREATE TABLE diaper_types_enum (
    id          SERIAL PRIMARY KEY,
    type        VARCHAR(50) NOT NULL UNIQUE,
    is_active  BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- object_types_enum → reference from: user_objects
CREATE TABLE object_types_enum (
    id           SERIAL PRIMARY KEY,
    object_name  VARCHAR(50) NOT NULL UNIQUE,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- item_conditions_enum → reference from: user_objects
CREATE TABLE item_conditions_enum (
    id             SERIAL PRIMARY KEY,
    condition_name VARCHAR(50) NOT NULL UNIQUE,
    is_active      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by      VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- return_reasons_enum → reference from: user_clothings
CREATE TABLE return_reasons_enum (
    id          SERIAL PRIMARY KEY,
    reason      VARCHAR(50) NOT NULL UNIQUE,
    is_active  BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- bathroom_turns → reference from: bathroom_schedules
CREATE TABLE bathroom_turns (
    id          SERIAL PRIMARY KEY,
    turn_name   VARCHAR(50) NOT NULL UNIQUE,
    start_at    TIME NOT NULL,
    end_at      TIME NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by  VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- ═══════════════════════════════════════════════════════════════════════
-- NIVEL 3: ENTIDADES QUE DEPENDEN DE CATÁLOGOS
-- ═══════════════════════════════════════════════════════════════════════

-- users → FK: sex_enum, user_dependency_enum
CREATE TABLE users (
    id              SERIAL PRIMARY KEY,
    first_name       VARCHAR(25)  NOT NULL,
    second_name     VARCHAR(25),
    first_surname   VARCHAR(25)  NOT NULL,
    second_surname VARCHAR(25),
    alias          VARCHAR(25),
    email          VARCHAR(255),
    phone          VARCHAR(20),
    cellphone      VARCHAR(20),
    dni_nie        VARCHAR(9)   NOT NULL,
    sex_id         INTEGER      NOT NULL REFERENCES sex_enum(id),
    birth_date     DATE         NOT NULL,
    dependency_id INTEGER      NOT NULL REFERENCES user_dependency_enum(id),
    is_active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by     VARCHAR(50)  NOT NULL DEFAULT 'system',
    updated_by     VARCHAR(50)  NOT NULL DEFAULT 'system'
);

-- workers → FK: NONE (solo auditing)
CREATE TABLE workers (
    id              SERIAL PRIMARY KEY,
    tenant_id       VARCHAR(50) NOT NULL,
    first_name     VARCHAR(25) NOT NULL,
    second_name   VARCHAR(25),
    first_surname VARCHAR(25) NOT NULL,
    second_surname VARCHAR(25),
    dni_nie       VARCHAR(9)  NOT NULL UNIQUE,
    main_phone   VARCHAR(20) NOT NULL,
    second_phone VARCHAR(20),
    email         VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    is_active    BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by      VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- route_vehicles → FK: NONE
CREATE TABLE route_vehicles (
    id                SERIAL PRIMARY KEY,
    license_plate    VARCHAR(10) NOT NULL UNIQUE,
    capacity         INTEGER NOT NULL,
    has_wheelchair  BOOLEAN NOT NULL,
    wheelchair_capacity INTEGER,
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by      VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- ═══════════════════════════════════════════════════════════════════════
-- NIVEL 4: ENTIDADES QUE DEPENDEN DE OTRAS ENTIDADES
-- ═══════════════════════════════════════════════════════════════════════

-- user_contacts → FK: users, user_relationships_enum
CREATE TABLE user_contacts (
    id                    SERIAL PRIMARY KEY,
    user_id                INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    contact_name           VARCHAR(25) NOT NULL,
    contact_relationship_id INTEGER NOT NULL REFERENCES user_relationships_enum(id),
    contact_phone         VARCHAR(20) NOT NULL,
    contact_email         VARCHAR(255) NOT NULL,
    contact_note          TEXT,
    is_contact_main      BOOLEAN NOT NULL DEFAULT FALSE,
    is_active            BOOLEAN NOT NULL DEFAULT TRUE,
    created_at            TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by            VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by            VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_addresses → FK: users, cities_enum, provinces_enum, regions_enum
CREATE TABLE user_addresses (
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER     NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    address        VARCHAR(100) NOT NULL,
    postal_code    VARCHAR(10) NOT NULL,
    city_id        INTEGER NOT NULL REFERENCES cities_enum(id),
    province_id    INTEGER NOT NULL REFERENCES provinces_enum(id),
    region_id      INTEGER NOT NULL REFERENCES regions_enum(id),
    is_active     BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_attendance_days → FK: users, open_days
CREATE TABLE user_attendance_days (
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    day_id         INTEGER NOT NULL REFERENCES open_days(id),
    start_at       TIME NOT NULL,
    end_at        TIME NOT NULL,
    is_active     BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_attendance_records → FK: users, user_attendance_days
CREATE TABLE user_attendance_records (
    id                SERIAL PRIMARY KEY,
    user_id           INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    attendance_day_id INTEGER NOT NULL REFERENCES user_attendance_days(id),
    is_present        BOOLEAN NOT NULL,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by       VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- workers_roles (junction) → FK: workers, roles_enum
CREATE TABLE workers_roles (
    id          SERIAL PRIMARY KEY,
    worker_id  INTEGER NOT NULL REFERENCES workers(id) ON DELETE CASCADE,
    role_id    INTEGER NOT NULL REFERENCES roles_enum(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- workers_schedules → FK: workers, open_days
CREATE TABLE workers_schedules (
    id              SERIAL PRIMARY KEY,
    worker_id       INTEGER NOT NULL REFERENCES workers(id) ON DELETE CASCADE,
    day_id         INTEGER NOT NULL REFERENCES open_days(id),
    start_at        TIME NOT NULL,
    end_at         TIME NOT NULL,
    is_active      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by     VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by     VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- workers_schedules_records → FK: workers, workers_schedules
CREATE TABLE workers_schedules_records (
    id              SERIAL PRIMARY KEY,
    worker_id       INTEGER NOT NULL REFERENCES workers(id),
    schedule_id     INTEGER NOT NULL REFERENCES workers_schedules(id),
    clock_in        TIMESTAMPTZ NOT NULL,
    clock_out       TIMESTAMPTZ,
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by      VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- transport_routes → FK: routes_shift_enum, route_vehicles, workers(x2)
CREATE TABLE transport_routes (
    id                SERIAL PRIMARY KEY,
    route_number     INTEGER NOT NULL,
    start_time       TIME NOT NULL,
    end_time         TIME NOT NULL,
    route_shift_id   INTEGER NOT NULL REFERENCES routes_shift_enum(id),
    route_vehicle_id INTEGER NOT NULL REFERENCES route_vehicles(id),
    worker_driver_id  INTEGER NOT NULL REFERENCES workers(id),
    worker_copilot_id INTEGER NOT NULL REFERENCES workers(id),
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by      VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- transport_routes_user (junction) → FK: transport_routes, users
CREATE TABLE transport_routes_user (
    id            SERIAL PRIMARY KEY,
    route_id      INTEGER NOT NULL REFERENCES transport_routes(id) ON DELETE CASCADE,
    user_id      INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- cd_incidents → FK: workers, incident_cd_enum, significances_cd_enum, incident_status_enum
CREATE TABLE cd_incidents (
    id                 SERIAL PRIMARY KEY,
    reported_by_id    INTEGER NOT NULL REFERENCES workers(id),
    incident_cd_id    INTEGER NOT NULL REFERENCES incident_cd_enum(id),
    significance_cd_id INTEGER NOT NULL REFERENCES significances_cd_enum(id),
    comment          TEXT NOT NULL,
    incident_status_id INTEGER NOT NULL REFERENCES incident_status_enum(id),
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by      VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- incidents_cd_comments → FK: cd_incidents, workers
CREATE TABLE incidents_cd_comments (
    id              SERIAL PRIMARY KEY,
    cd_incident_id  INTEGER NOT NULL REFERENCES cd_incidents(id) ON DELETE CASCADE,
    worker_id       INTEGER NOT NULL REFERENCES workers(id),
    comment        TEXT NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by     VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by     VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- users_incidents → FK: users, workers, incidents_user_enum, significances_user_enum, incident_status_enum
CREATE TABLE users_incidents (
    id                    SERIAL PRIMARY KEY,
    user_id               INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    reported_by_id       INTEGER NOT NULL REFERENCES workers(id),
    incident_user_id    INTEGER NOT NULL REFERENCES incidents_user_enum(id),
    significance_user_id INTEGER NOT NULL REFERENCES significances_user_enum(id),
    comment            TEXT NOT NULL,
    incident_status_id INTEGER NOT NULL REFERENCES incident_status_enum(id),
    is_active         BOOLEAN NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by       VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- incidents_users_comments → FK: users_incidents, workers
CREATE TABLE incidents_users_comments (
    id                SERIAL PRIMARY KEY,
    user_incident_id INTEGER NOT NULL REFERENCES users_incidents(id) ON DELETE CASCADE,
    worker_id       INTEGER NOT NULL REFERENCES workers(id),
    comment         TEXT NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by     VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by     VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- bathroom_schedules → FK: users, bathroom_turns, bathroom_tasks_enum
CREATE TABLE bathroom_schedules (
    id                SERIAL PRIMARY KEY,
    user_id          INTEGER NOT NULL REFERENCES users(id),
    bathroom_turn_id INTEGER NOT NULL REFERENCES bathroom_turns(id),
    bathroom_task_id INTEGER NOT NULL REFERENCES bathroom_tasks_enum(id),
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by      VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_clothings → FK: clothing_types_enum, return_reasons_enum
CREATE TABLE user_clothings (
    id                SERIAL PRIMARY KEY,
    clothing_type_id   INTEGER NOT NULL REFERENCES clothing_types_enum(id),
    is_clean        BOOLEAN NOT NULL,
    is_returned     BOOLEAN NOT NULL,
    received_at    TIMESTAMPTZ NOT NULL,
    returned_at    TIMESTAMPTZ,
    return_reason_id INTEGER NOT NULL REFERENCES return_reasons_enum(id),
    is_active     BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_objects → FK: item_conditions_enum, object_types_enum
CREATE TABLE user_objects (
    id                SERIAL PRIMARY KEY,
    item_condition_id INTEGER NOT NULL REFERENCES item_conditions_enum(id),
    object_type_id   INTEGER NOT NULL REFERENCES object_types_enum(id),
    comment          TEXT,
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by      VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_diapers → FK: diaper_sizes_enum, diaper_types_enum
CREATE TABLE user_diapers (
    id                SERIAL PRIMARY KEY,
    diaper_size_id    INTEGER NOT NULL REFERENCES diaper_sizes_enum(id),
    diaper_type_id   INTEGER NOT NULL REFERENCES diaper_types_enum(id),
    quantity        INTEGER NOT NULL,
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by      VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_belongings → FK: users, (user_clothings|user_objects|user_diapers), workers
CREATE TABLE user_belongings (
    id                SERIAL PRIMARY KEY,
    user_id          INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    user_clothing_id  INTEGER REFERENCES user_clothings(id) ON DELETE CASCADE,
    user_object_id   INTEGER REFERENCES user_objects(id) ON DELETE CASCADE,
    user_diaper_id  INTEGER REFERENCES user_diapers(id) ON DELETE CASCADE,
    worker_id       INTEGER NOT NULL REFERENCES workers(id),
    comment         TEXT,
    is_request     BOOLEAN NOT NULL,
    is_active     BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by    VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_medical_info → FK: users, workers
CREATE TABLE user_medical_info (
    id            SERIAL PRIMARY KEY,
    user_id       INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    worker_id     INTEGER NOT NULL REFERENCES workers(id),
    is_active     BOOLEAN NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by   VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by   VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_allergies → FK: user_medical_info, allergies_enum
CREATE TABLE user_allergies (
    id                 SERIAL PRIMARY KEY,
    user_medical_info_id INTEGER NOT NULL REFERENCES user_medical_info(id) ON DELETE CASCADE,
    allergy_id           INTEGER NOT NULL REFERENCES allergies_enum(id),
    comment            TEXT,
    is_active         BOOLEAN NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by       VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- user_illnesses → FK: user_medical_info, illnesses_enum
CREATE TABLE user_illnesses (
    id                 SERIAL PRIMARY KEY,
    user_medical_info_id INTEGER NOT NULL REFERENCES user_medical_info(id) ON DELETE CASCADE,
    illness_id          INTEGER NOT NULL REFERENCES illnesses_enum(id),
    is_active         BOOLEAN NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by       VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- medications → FK: medication_names_enum, medication_applications_enum, storage_conditions_enum
CREATE TABLE medications (
    id                       SERIAL PRIMARY KEY,
    medication_name_id     INTEGER NOT NULL REFERENCES medication_names_enum(id),
    medication_application_id INTEGER NOT NULL REFERENCES medication_applications_enum(id),
    dose                    VARCHAR(100) NOT NULL,
    reception_date         DATE NOT NULL,
    expiration_date       DATE NOT NULL,
    storage_condition_id  INTEGER NOT NULL REFERENCES storage_conditions_enum(id),
    is_active              BOOLEAN NOT NULL DEFAULT TRUE,
    created_at             TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by             VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by             VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- treatment_details → FK: NONE (solo auditing)
CREATE TABLE treatment_details (
    id           SERIAL PRIMARY KEY,
    start_date   DATE NOT NULL,
    end_date    DATE,
    is_active  BOOLEAN NOT NULL DEFAULT TRUE,
    comment    TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by VARCHAR(50) NOT NULL DEFAULT 'system'
);

-- treatment_details_medication (junction) → FK: treatment_details, medications
CREATE TABLE treatment_details_medication (
    id                   SERIAL PRIMARY KEY,
    treatment_detail_id INTEGER NOT NULL REFERENCES treatment_details(id) ON DELETE CASCADE,
    medication_id      INTEGER NOT NULL REFERENCES medications(id) ON DELETE CASCADE,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- user_illness_treatment_details (junction) → FK: user_illnesses, treatment_details
CREATE TABLE user_illness_treatment_details (
    id               SERIAL PRIMARY KEY,
    user_illness_id      INTEGER NOT NULL REFERENCES user_illnesses(id) ON DELETE CASCADE,
    treatment_detail_id INTEGER NOT NULL REFERENCES treatment_details(id) ON DELETE CASCADE,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- allergies_medications (junction) → FK: user_allergies, medications
CREATE TABLE allergies_medications (
    id               SERIAL PRIMARY KEY,
    user_allergy_id  INTEGER NOT NULL REFERENCES user_allergies(id) ON DELETE CASCADE,
    medication_id    INTEGER NOT NULL REFERENCES medications(id) ON DELETE CASCADE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ═══════════════════════════════════════════════════════════════════════
-- ÍNDICES (al final, no tienen orden de dependencias)
-- ═══════════════════════════════════════════════════════════════════════

CREATE UNIQUE INDEX idx_users_dni ON users(dni_nie);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_is_active ON users(is_active);

CREATE INDEX idx_contacts_user ON user_contacts(user_id);

CREATE INDEX idx_addresses_user ON user_addresses(user_id);

CREATE INDEX idx_attendance_day_user ON user_attendance_days(user_id);
CREATE INDEX idx_attendance_record_user ON user_attendance_records(user_id);
CREATE INDEX idx_attendance_record_day ON user_attendance_records(attendance_day_id);

CREATE UNIQUE INDEX idx_workers_dni ON workers(dni_nie);
CREATE INDEX idx_workers_email ON workers(email);

CREATE INDEX idx_schedule_worker ON workers_schedules(worker_id);
CREATE INDEX idx_schedule_record_worker ON workers_schedules_records(worker_id);
CREATE INDEX idx_schedule_record_schedule ON workers_schedules_records(schedule_id);

CREATE INDEX idx_medical_info_user ON user_medical_info(user_id);
CREATE INDEX idx_allergies_medical_info ON user_allergies(user_medical_info_id);
CREATE INDEX idx_illnesses_medical_info ON user_illnesses(user_medical_info_id);

CREATE INDEX idx_belongings_user ON user_belongings(user_id);
CREATE INDEX idx_objects_belonging ON user_objects(id);
CREATE INDEX idx_clothings_belonging ON user_clothings(id);
CREATE INDEX idx_diapers_belonging ON user_diapers(id);

CREATE INDEX idx_routes_active ON transport_routes(is_active);
CREATE INDEX idx_vehicles_active ON route_vehicles(is_active);

CREATE INDEX idx_bathroom_schedule_user ON bathroom_schedules(user_id);