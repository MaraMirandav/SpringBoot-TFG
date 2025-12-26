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

------------------
SELECT * FROM workers;

SELECT * FROM open_days;

SELECT * FROM workers_schedules;

SELECT * FROM workers_schedules_records;

SELECT w.first_name, ws.start_at, ws.end_at
FROM workers w
JOIN workers_schedules ws ON w.id = ws.worker_id;



-- VER trabajador 3, su horario y su fichaje

SELECT w.first_name, d.day, ws.start_at, ws.end_at, wr.clock_in, wr.clock_out
FROM schema_template.workers w
JOIN schema_template.workers_schedules ws ON w.id = ws.worker_id
JOIN schema_template.open_days d ON ws.day_id = d.id
JOIN schema_template.workers_schedules_records wr ON wr.schedule_id = ws.id
WHERE w.id = 5;



SELECT * FROM workers_schedules_records WHERE worker_id = 3;


SELECT
    w.first_name,
    d.day,
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
JOIN schema_template.significance_cd_enum sig ON i.significance_cd_id = sig.id
JOIN schema_template.workers w_creator ON i.created_by_worker_id = w_creator.id
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
JOIN schema_template.incident_user_enum cat ON i.incident_user_id = cat.id
JOIN schema_template.significance_user_enum sig ON i.significance_user_id = sig.id
JOIN schema_template.workers w_creator ON i.created_by_worker_id = w_creator.id
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
JOIN schema_template.significance_cd_enum sig ON i.significance_cd_id = sig.id
JOIN schema_template.workers w_creator ON i.created_by_worker_id = w_creator.id
LEFT JOIN schema_template.incidents_cd_comments c ON c.cd_incident_id = i.id
LEFT JOIN schema_template.workers w_comment ON c.worker_id = w_comment.id
ORDER BY i.created_at DESC, c.created_at ASC;

------------------
-- Roles y posiciones

SELECT
    w.id,
    CONCAT(w.first_name, ' ', w.first_surname) AS nombre_completo,
    STRING_AGG(DISTINCT p.position_name, ' - ') AS cargos,
    STRING_AGG(DISTINCT r.role_name, ' - ') AS roles_app
FROM schema_template.workers w
LEFT JOIN schema_template.workers_positions wp ON w.id = wp.worker_id
LEFT JOIN schema_template.positions_enum p ON wp.position_id = p.id
LEFT JOIN schema_template.workers_roles wr ON w.id = wr.worker_id
LEFT JOIN schema_template.roles_enum r ON wr.role_id = r.id
GROUP BY w.id, w.first_name, w.first_surname
ORDER BY w.id ASC;