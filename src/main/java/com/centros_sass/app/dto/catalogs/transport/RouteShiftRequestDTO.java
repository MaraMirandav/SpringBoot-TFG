package com.centros_sass.app.dto.catalogs.transport;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RouteShiftRequestDTO(

    @NotBlank(message = "El turno de ruta es obligatorio")
    @Size(max = 20, message = "El turno de ruta no puede exceder los 20 caracteres")
    String routeName

) {}
