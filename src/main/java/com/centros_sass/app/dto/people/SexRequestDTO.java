package com.centros_sass.app.dto.people;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SexRequestDTO(

    @NotBlank(message = "El sexo es obligatorio")
    @Size(max = 20, message = "El sexo no puede exceder los 20 caracteres")
    String sex

) {}
