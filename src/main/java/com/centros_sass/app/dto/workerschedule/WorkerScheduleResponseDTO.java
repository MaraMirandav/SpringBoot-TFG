package com.centros_sass.app.dto.workerschedule;

import java.time.LocalTime;

/**
 * DTO de respuesta para retornar datos de un horario de trabajador.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record WorkerScheduleResponseDTO(
    Integer id,
    Integer workerId,
    String workerFullName,
    Integer openDayId,
    String dayName,
    LocalTime startAt,
    LocalTime endAt,
    Boolean isActive,
    Boolean hasRecord,
    String createdAt,
    String updatedAt
) {}
