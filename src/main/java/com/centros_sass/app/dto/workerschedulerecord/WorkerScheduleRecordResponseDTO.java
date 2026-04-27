package com.centros_sass.app.dto.workerschedulerecord;

import java.time.LocalDateTime;

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
