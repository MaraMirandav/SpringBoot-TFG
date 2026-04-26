package com.centros_sass.app.dto.catalogs.treatments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MedicationApplicationRequestDTO(

    @NotBlank(message = "El aplicación de medicamento es obligatorio")
    @Size(max = 100, message = "El aplicación de medicamento no puede exceder los 100 caracteres")
    String medicationApplicationName

) {}
