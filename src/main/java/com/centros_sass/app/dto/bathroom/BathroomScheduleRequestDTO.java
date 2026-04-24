package com.centros_sass.app.dto.bathroom;

import jakarta.validation.constraints.NotNull;

/**
 * DTO de solicitud para crear un registro de higiene.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record BathroomScheduleRequestDTO(

    @NotNull(message = "El usuario es obligatorio")
    Integer userId,

    @NotNull(message = "El turno de baño es obligatorio")
    Integer bathroomTurnId,

    @NotNull(message = "La tarea de higiene es obligatoria")
    Integer bathroomTaskId

) {}
