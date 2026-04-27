package com.centros_sass.app.dto.catalogs.incidents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IncidentStatusRequestDTO(

    @NotBlank(message = "El estado de incidencia es obligatorio")
    @Size(max = 50, message = "El estado de incidencia no puede exceder los 50 caracteres")
    String statusName

) {}
