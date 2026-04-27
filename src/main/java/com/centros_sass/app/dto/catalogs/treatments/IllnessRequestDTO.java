package com.centros_sass.app.dto.catalogs.treatments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IllnessRequestDTO(

    @NotBlank(message = "El enfermedad es obligatorio")
    @Size(max = 100, message = "El enfermedad no puede exceder los 100 caracteres")
    String illnessName

) {}
