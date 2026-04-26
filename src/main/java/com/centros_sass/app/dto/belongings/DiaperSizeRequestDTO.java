package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DiaperSizeRequestDTO(

    @NotBlank(message = "El tamaño de pañal es obligatorio")
    @Size(max = 20, message = "El tamaño de pañal no puede exceder los 20 caracteres")
    String size

) {}
