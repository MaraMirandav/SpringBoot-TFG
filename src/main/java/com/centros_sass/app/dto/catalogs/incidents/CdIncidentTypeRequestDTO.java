package com.centros_sass.app.dto.catalogs.incidents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CdIncidentTypeRequestDTO(

    @NotBlank(message = "El tipo de incidencia del centro es obligatorio")
    @Size(max = 50, message = "El tipo de incidencia del centro no puede exceder los 50 caracteres")
    String incidentName

) {}
