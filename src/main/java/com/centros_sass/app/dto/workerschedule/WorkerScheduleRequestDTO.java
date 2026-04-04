package com.centros_sass.app.dto.workerschedule;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

/**
 * DTO de solicitud para crear un horario de trabajador.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record WorkerScheduleRequestDTO(
    @NotNull(message = "El ID del trabajador es obligatorio")
    Integer workerId,

    @NotNull(message = "El día es obligatorio")
    Integer openDayId,

    @NotNull(message = "La hora de entrada es obligatoria")
    LocalTime startAt,

    @NotNull(message = "La hora de salida es obligatoria")
    LocalTime endAt
) {}
