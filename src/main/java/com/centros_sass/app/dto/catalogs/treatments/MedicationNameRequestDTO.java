package com.centros_sass.app.dto.catalogs.treatments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MedicationNameRequestDTO(

    @NotBlank(message = "El medicamento es obligatorio")
    @Size(max = 100, message = "El medicamento no puede exceder los 100 caracteres")
    String medicationName

) {}
