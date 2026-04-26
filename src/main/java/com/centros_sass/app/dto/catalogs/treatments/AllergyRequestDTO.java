package com.centros_sass.app.dto.catalogs.treatments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AllergyRequestDTO(

    @NotBlank(message = "El alergia es obligatorio")
    @Size(max = 100, message = "El alergia no puede exceder los 100 caracteres")
    String allergyName

) {}
