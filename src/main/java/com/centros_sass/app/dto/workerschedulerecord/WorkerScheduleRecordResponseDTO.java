package com.centros_sass.app.dto.workerschedulerecord;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para retornar datos de un fichaje.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record WorkerScheduleRecordResponseDTO(
    Integer id,
    Integer workerId,
    String workerFullName,
    Integer scheduleId,
    LocalDateTime clockIn,
    LocalDateTime clockOut,
    String hoursWorked,
    Boolean isLate,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
