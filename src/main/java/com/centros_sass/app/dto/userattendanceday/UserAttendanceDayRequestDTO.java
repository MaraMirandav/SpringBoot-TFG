package com.centros_sass.app.dto.userattendanceday;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record UserAttendanceDayRequestDTO(

    @NotNull(message = "El usuario es obligatorio")
    Integer userId,

    @NotNull(message = "El día es obligatorio")
    Integer dayId,

    @NotNull(message = "La hora de inicio es obligatoria")
    LocalTime startAt,

    @NotNull(message = "La hora de fin es obligatoria")
    LocalTime endAt

) {}
