package com.centros_sass.app.dto.catalogs.calendar;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OpenDayRequestDTO(

    @NotBlank(message = "El día es obligatorio")
    @Size(max = 20, message = "El día no puede exceder los 20 caracteres")
    String dayName,

    @NotNull(message = "La hora de apertura es obligatoria")
    LocalTime openAt,

    @NotNull(message = "La hora de cierre es obligatoria")
    LocalTime closeAt

) {}
