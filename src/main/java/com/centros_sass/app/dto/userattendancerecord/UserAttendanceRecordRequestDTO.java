package com.centros_sass.app.dto.userattendancerecord;

import jakarta.validation.constraints.NotNull;

public record UserAttendanceRecordRequestDTO(

        @NotNull(message = "El ID del usuario es obligatorio")
        Integer userId,

        @NotNull(message = "El ID del día de asistencia es obligatorio")
        Integer attendanceDayId,

        @NotNull(message = "El estado de presencia es obligatorio")
        Boolean isPresent

) {}
