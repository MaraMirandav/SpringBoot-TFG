package com.centros_sass.app.dto.bathroom;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear un turno de baño.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record BathroomTurnRequestDTO(

    @NotBlank(message = "El nombre del turno es obligatorio")
    @Size(max = 50, message = "El nombre del turno no puede exceder los 50 caracteres")
    String turnName,

    @NotNull(message = "La hora de inicio es obligatoria")
    LocalTime startAt,

    @NotNull(message = "La hora de fin es obligatoria")
    LocalTime endAt

) {}
