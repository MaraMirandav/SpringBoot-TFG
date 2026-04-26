package com.centros_sass.app.dto.bathroom;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BathroomTaskRequestDTO(

    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(max = 50, message = "El nombre de la tarea no puede exceder los 50 caracteres")
    String taskName,

    @NotNull(message = "El tiempo estimado es obligatorio")
    LocalTime estimatedTime

) {}
