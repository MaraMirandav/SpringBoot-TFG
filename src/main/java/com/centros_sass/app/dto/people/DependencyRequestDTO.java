package com.centros_sass.app.dto.people;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DependencyRequestDTO(

    @NotBlank(message = "El nivel de dependencia es obligatorio")
    @Size(max = 50, message = "El nivel de dependencia no puede exceder los 50 caracteres")
    String dependencyLevel

) {}
