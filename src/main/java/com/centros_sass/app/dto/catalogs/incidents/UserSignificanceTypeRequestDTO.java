package com.centros_sass.app.dto.catalogs.incidents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserSignificanceTypeRequestDTO(

    @NotBlank(message = "El tipo de significancia de usuario es obligatorio")
    @Size(max = 50, message = "El tipo de significancia de usuario no puede exceder los 50 caracteres")
    String significanceName

) {}
