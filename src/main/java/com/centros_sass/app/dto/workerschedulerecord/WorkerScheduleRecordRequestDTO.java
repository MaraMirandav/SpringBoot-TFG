package com.centros_sass.app.dto.workerschedulerecord;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record WorkerScheduleRecordRequestDTO(
    @NotNull(message = "El ID del horario es obligatorio")
    Integer scheduleId,

    @NotNull(message = "La hora de entrada es obligatoria")
    @PastOrPresent(message = "La hora de entrada no puede ser futura")
    LocalDateTime clockIn
) {}
