package com.centros_sass.app.dto.catalogs.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DiaperTypeRequestDTO(

    @NotBlank(message = "El tipo de pañal es obligatorio")
    @Size(max = 50, message = "El tipo de pañal no puede exceder los 50 caracteres")
    String type

) {}
