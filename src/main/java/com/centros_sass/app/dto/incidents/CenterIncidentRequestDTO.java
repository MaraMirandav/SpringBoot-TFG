package com.centros_sass.app.dto.incidents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CenterIncidentRequestDTO(

    @NotNull(message = "El trabajador que reporta es obligatorio")
    Integer reportedById,

    @NotBlank(message = "El comentario es obligatorio")
    @Size(max = 1000, message = "El comentario no puede exceder los 1000 caracteres")
    String comment,

    Integer incidentStatusId,

    @NotNull(message = "El tipo de incidencia del centro es obligatorio")
    Integer cdIncidentTypeId,

    @NotNull(message = "El nivel de significancia del centro es obligatorio")
    Integer cdSignificanceTypeId

) {}
