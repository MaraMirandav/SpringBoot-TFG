package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ObjectTypeRequestDTO(

    @NotBlank(message = "El nombre del objeto es obligatorio")
    @Size(max = 50, message = "El nombre del objeto no puede exceder los 50 caracteres")
    String objectName

) {}
