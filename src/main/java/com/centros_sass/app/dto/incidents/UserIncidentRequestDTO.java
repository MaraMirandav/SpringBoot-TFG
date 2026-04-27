package com.centros_sass.app.dto.incidents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserIncidentRequestDTO(

    @NotNull(message = "El usuario es obligatorio")
    Integer userId,

    @NotNull(message = "El trabajador que reporta es obligatorio")
    Integer reportedById,

    @NotBlank(message = "El comentario es obligatorio")
    @Size(max = 1000, message = "El comentario no puede exceder los 1000 caracteres")
    String comment,

    Integer incidentStatusId,

    @NotNull(message = "El tipo de incidencia de usuario es obligatorio")
    Integer userIncidentTypeId,

    @NotNull(message = "El nivel de significancia de usuario es obligatorio")
    Integer userSignificanceTypeId

) {}
