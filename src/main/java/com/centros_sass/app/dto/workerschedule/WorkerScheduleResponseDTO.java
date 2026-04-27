package com.centros_sass.app.dto.workerschedule;

import java.time.LocalTime;

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
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
