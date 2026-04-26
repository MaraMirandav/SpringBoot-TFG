package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReturnReasonRequestDTO(

    @NotBlank(message = "La razón de devolución es obligatoria")
    @Size(max = 50, message = "La razón de devolución no puede exceder los 50 caracteres")
    String reason

) {}
