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
