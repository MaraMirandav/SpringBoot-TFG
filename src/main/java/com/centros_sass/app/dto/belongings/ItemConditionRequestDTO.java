package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ItemConditionRequestDTO(

    @NotBlank(message = "El nombre de la condición es obligatorio")
    @Size(max = 50, message = "El nombre de la condición no puede exceder los 50 caracteres")
    String conditionName

) {}
