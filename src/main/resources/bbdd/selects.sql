SELECT * FROM users;

SELECT
    u.id,
    u.first_name AS "Nombre",
    u.alias AS "Alias",
    s.sex AS "Sexo",
    d.dependency_level AS "Nivel Dependencia"
FROM schema_template.users u
JOIN schema_template.sex_enum s ON u.sex_id = s.id
JOIN schema_template.user_dependency_enum d ON u.dependency_id = d.id;

------------------
SELECT * FROM workers;

SELECT * FROM open_days;

SELECT * FROM workers_schedules;

SELECT * FROM workers_schedules_records;

SELECT w.first_name, ws.start_at, ws.end_at
FROM workers w
JOIN workers_schedules ws ON w.id = ws.worker_id;



-- VER trabajador 3, su horario y su fichaje

SELECT w.first_name, d.day_name, ws.start_at, ws.end_at, wr.clock_in, wr.clock_out
FROM schema_template.workers w
JOIN schema_template.workers_schedules ws ON w.id = ws.worker_id
JOIN schema_template.open_days d ON ws.day_id = d.id
JOIN schema_template.workers_schedules_records wr ON wr.schedule_id = ws.id
WHERE w.id = 5;


SELECT * FROM workers_schedules_records WHERE worker_id = 3;


SELECT
    w.first_name,
    d.day_name,
    ws.start_at,
    ws.end_at,
    wr.clock_in,
    wr.clock_out
FROM schema_template.workers w
LEFT JOIN schema_template.workers_schedules ws
    ON w.id = ws.worker_id
LEFT JOIN schema_template.open_days d
    ON ws.day_id = d.id
LEFT JOIN schema_template.workers_schedules_records wr
    ON wr.worker_id = w.id
    AND wr.clock_in::time BETWEEN ws.start_at AND ws.end_at
WHERE w.id = 3
ORDER BY d.id, wr.clock_in;

------------------

-- Incidencias del Centro
SELECT
    i.id AS "ID",
    cat.incident_name AS "Tipo Problema",
    sig.significance_name AS "Gravedad",
    w_creator.first_name AS "Reportado Por",
    i.comment AS "Descripción",
    w_comment.first_name AS "Respondió",
    c.comment AS "Solución"
FROM schema_template.cd_incidents i
JOIN schema_template.incident_cd_enum cat ON i.incident_cd_id = cat.id
JOIN schema_template.significances_cd_enum sig ON i.significance_cd_id = sig.id
JOIN schema_template.workers w_creator ON i.reported_by_id = w_creator.id
LEFT JOIN schema_template.incidents_cd_comments c ON c.cd_incident_id = i.id
LEFT JOIN schema_template.workers w_comment ON c.worker_id = w_comment.id
ORDER BY i.id;

-- Incidencias del Usuario
SELECT
    TO_CHAR(i.created_at, 'DD/MM/YYYY HH24:MI') AS "Fecha Hora Incidencia",
    u.first_name || ' ' || u.first_surname AS "Usuario",
    cat.incident_name AS "Tipo",
    sig.significance_name AS "Gravedad",
    w_creator.first_name AS "Creada Por",
    LEFT(i.comment, 40) || '...' AS "Resumen Incidencia",
    TO_CHAR(c.created_at, 'DD/MM/YYYY HH24:MI') AS "Fecha Hora Comentario",
    w_comment.first_name AS "Comentada Por",
    c.comment AS "Texto Comentario"
FROM schema_template.users_incidents i
JOIN schema_template.users u ON i.user_id = u.id
JOIN schema_template.incidents_user_enum cat ON i.incident_user_id = cat.id
JOIN schema_template.significances_user_enum sig ON i.significance_user_id = sig.id
JOIN schema_template.workers w_creator ON i.reported_by_id = w_creator.id
LEFT JOIN schema_template.incidents_users_comments c ON c.user_incident_id = i.id
LEFT JOIN schema_template.workers w_comment ON c.worker_id = w_comment.id
ORDER BY i.created_at DESC, c.created_at ASC;

-- Incidencias del Centro con fechas
SELECT
    TO_CHAR(i.created_at, 'DD/MM/YYYY HH24:MI') AS "Fecha Hora Incidencia",
    cat.incident_name AS "Tipo",
    sig.significance_name AS "Gravedad",
    w_creator.first_name AS "Creada Por",
    i.comment AS "Problema Completo",
    TO_CHAR(c.created_at, 'DD/MM/YYYY HH24:MI') AS "Fecha Hora Comentario",
    w_comment.first_name AS "Comentada Por",
    c.comment AS "Respuesta"
FROM schema_template.cd_incidents i
JOIN schema_template.incident_cd_enum cat ON i.incident_cd_id = cat.id
JOIN schema_template.significances_cd_enum sig ON i.significance_cd_id = sig.id
JOIN schema_template.workers w_creator ON i.reported_by_id = w_creator.id
LEFT JOIN schema_template.incidents_cd_comments c ON c.cd_incident_id = i.id
LEFT JOIN schema_template.workers w_comment ON c.worker_id = w_comment.id
ORDER BY i.created_at DESC, c.created_at ASC;

------------------
-- Roles

SELECT
    w.id,
    CONCAT(w.first_name, ' ', w.first_surname) AS nombre_completo,
    STRING_AGG(DISTINCT r.role_name, ' - ') AS roles_app
FROM schema_template.workers w
LEFT JOIN schema_template.workers_roles wr ON w.id = wr.worker_id
LEFT JOIN schema_template.roles_enum r ON wr.role_id = r.id
GROUP BY w.id, w.first_name, w.first_surname
ORDER BY w.id ASC;

------------------
-- Direcciones

SELECT
    CONCAT(u.first_name, ' ', u.first_surname) AS nombre_completo,
    ua.address AS "Dirección",
    c.city_name AS "Ciudad",
    p.province_name AS "Provincia"
FROM users u
JOIN user_addresses ua ON u.id = ua.user_id
JOIN cities_enum c ON ua.city_id = c.id
JOIN provinces_enum p ON ua.province_id = p.id;

------------------
-- Contactos
SELECT
    u.first_name AS "Usuario",
    uc.contact_name AS "Contacto",
    r.relationship_name AS "Relación",
    uc.contact_phone AS "Teléfono",
    uc.contact_note AS "Notas Importantes"
FROM user_contacts uc
JOIN users u ON uc.user_id = u.id
JOIN user_relationships_enum r ON uc.contact_relationship_id = r.id
WHERE uc.is_contact_main = true;

------------------
-- Horarios Asistencia
SELECT
    od.day_name AS "Día Semana",
    CONCAT(u.first_name, ' ', u.first_surname) AS "Usuario",
    u.dni_nie,
    uad.start_at AS "Hora Entrada",
    uad.end_at AS "Hora Salida"
FROM user_attendance_days uad
JOIN users u ON uad.user_id = u.id
JOIN open_days od ON uad.day_id = od.id
WHERE od.day_name = 'Lunes'
ORDER BY u.first_surname;

select * from incident_status_enum;

------------------
-- Incidencias Usuarios (DASHBOARD)
SELECT
iue.incident_name as "Tipo",
ui.comment as "Incidencia",
CONCAT(u.first_name, ' ', u.first_surname) as "Usuario",
sue.significance_name as "Importancia",
ui.created_at as "Incio",
ui.updated_at as "Actualización",
CONCAT(w.first_name, ' ', w.first_surname) as "Creado Por",
ise.status_name as "Estado"
FROM users_incidents ui
JOIN users u ON ui.user_id = u.id
JOIN incidents_user_enum iue ON ui.incident_user_id = iue.id
JOIN significances_user_enum sue ON ui.significance_user_id = sue.id
JOIN workers w ON ui.reported_by_id = w.id
JOIN incident_status_enum ise ON ui.incident_status_id = ise.id
ORDER BY ui.created_at DESC;

------------------
-- Pruebas de tablas de transporte
SELECT
    tr.route_number AS "Ruta",
    rs.route_name AS "Turno",
    rv.license_plate AS "Matrícula",
    COUNT(tru.user_id) AS "Total Pasajeros"
FROM transport_routes tr
JOIN routes_shift_enum rs ON tr.route_shift_id = rs.id
JOIN route_vehicles rv ON tr.route_vehicle_id = rv.id
LEFT JOIN transport_routes_user tru ON tr.id = tru.route_id
GROUP BY tr.route_number, rs.route_name, rv.license_plate;

------------------
-- Pruebas Inventario
SELECT
    CONCAT(u.first_name, ' ', u.first_surname) AS "Usuario",
    CONCAT(w.first_name, ' ', w.first_surname) AS "Registrado Por",
    c_enum.clothing_name AS "Prenda",
    b.created_at AS "Hora Entrada",
    b.comment AS "Observaciones"
FROM user_belongings b
JOIN users u ON b.user_id = u.id
JOIN workers w ON b.worker_id = w.id
JOIN user_clothings uc ON b.user_clothing_id = uc.id
JOIN clothing_types_enum c_enum ON uc.clothing_type_id = c_enum.id;


SELECT
    CONCAT(u.first_name, ' ', u.first_surname) AS "Usuario",
    c_enum.clothing_name AS "Ropa",
    o_enum.object_name AS "Objeto",
    d_type.type AS "Tipo Pañal",
    ud.quantity AS "Cantidad"
FROM user_belongings b
JOIN users u ON b.user_id = u.id
JOIN user_clothings uc ON b.user_clothing_id = uc.id
JOIN clothing_types_enum c_enum ON uc.clothing_type_id = c_enum.id
LEFT JOIN user_objects uo ON b.user_object_id = uo.id
LEFT JOIN object_types_enum o_enum ON uo.object_type_id = o_enum.id
LEFT JOIN user_diapers ud ON b.user_diaper_id = ud.id
LEFT JOIN diaper_types_enum d_type ON ud.diaper_type_id = d_type.id;

------------------
-- Tratamientos

-- Enfermedad, tratamiento, medicación y dosis.
SELECT
    CONCAT(u.first_name, ' ', u.first_surname) AS usuario,
    ill.illness_name AS enfermedad,
    td.comment AS pauta_tratamiento,
    mn.medication_name AS medicamento,
    m.dose AS dosis
FROM users u
JOIN user_medical_info umi ON u.id = umi.user_id
JOIN user_illnesses ui ON umi.id = ui.user_medical_info_id
JOIN illnesses_enum ill ON ui.illness_id = ill.id
LEFT JOIN user_illness_treatment_details uit ON ui.id = uit.user_illness_id
LEFT JOIN treatment_details td ON uit.treatment_detail_id = td.id
LEFT JOIN treatment_details_medication tdm ON td.id = tdm.treatment_detail_id
LEFT JOIN medications m ON tdm.medication_id = m.id
LEFT JOIN medication_names_enum mn ON m.medication_name_id = mn.id
ORDER BY u.first_name;

-- Alergias
SELECT
    CONCAT(u.first_name, ' ', u.first_surname) AS paciente,
    ae.allergy_name AS alergia,
    ua.comment AS nota_medica
FROM users u
JOIN user_medical_info umi ON u.id = umi.user_id
JOIN user_allergies ua ON umi.id = ua.user_medical_info_id
JOIN allergies_enum ae ON ua.allergy_id = ae.id;

SELECT
    CONCAT(u.first_name, ' ', u.first_surname) AS usuario,
    ae.allergy_name AS tiene_alergia_a,
    ua.comment AS gravedad_o_nota,
    mn.medication_name AS medicamento_indicado,
    m.dose AS dosis_pautada
FROM users u
JOIN user_medical_info umi ON u.id = umi.user_id
JOIN user_allergies ua ON umi.id = ua.user_medical_info_id
JOIN allergies_enum ae ON ua.allergy_id = ae.id
JOIN allergies_medications am ON ua.id = am.user_allergy_id
JOIN medications m ON am.medication_id = m.id
JOIN medication_names_enum mn ON m.medication_name_id = mn.id;

-- Alergias (con y sin medicación)
SELECT
    CONCAT(u.first_name, ' ', u.first_surname) AS usuario,
    ae.allergy_name AS alergia,
    -- Usamos COALESCE para que escriba "Sin Medicación" en vez de NULL si está vacío
    COALESCE(mn.medication_name, 'Sin Medicación') AS tratamiento
FROM users u
JOIN user_medical_info umi ON u.id = umi.user_id
JOIN user_allergies ua ON umi.id = ua.user_medical_info_id
JOIN allergies_enum ae ON ua.allergy_id = ae.id
-- Aquí usamos LEFT JOIN para que no desaparezcan los que no toman nada
LEFT JOIN allergies_medications am ON ua.id = am.user_allergy_id
LEFT JOIN medications m ON am.medication_id = m.id
LEFT JOIN medication_names_enum mn ON m.medication_name_id = mn.id;

------------------
-- Baños e Higiene

-- Agenda general sobre los horarios y tareas de higiene
SELECT
    bt.start_at AS Hora,
    bt.turn_name AS Turno,
    CONCAT(u.first_name, ' ', u.first_surname) AS Paciente,
    bk.task_name AS Tarea,
    bk.estimated_time AS Duracion
FROM bathroom_schedules bs
JOIN users u ON bs.user_id = u.id
JOIN bathroom_turns bt ON bs.bathroom_turn_id = bt.id
JOIN bathroom_tasks_enum bk ON bs.bathroom_task_id = bk.id
ORDER BY bt.start_at, u.first_name;

-- Carga de trabajo por turno
SELECT
    bt.turn_name AS Turno,
    bt.start_at AS Inicio,
    COUNT(bs.id) AS Total_Pacientes
FROM bathroom_turns bt
LEFT JOIN bathroom_schedules bs ON bt.id = bs.bathroom_turn_id
GROUP BY bt.id, bt.turn_name, bt.start_at
ORDER BY bt.start_at;