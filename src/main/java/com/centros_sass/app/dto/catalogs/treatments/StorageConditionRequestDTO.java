package com.centros_sass.app.dto.catalogs.treatments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StorageConditionRequestDTO(

    @NotBlank(message = "El condición de almacenamiento es obligatorio")
    @Size(max = 100, message = "El condición de almacenamiento no puede exceder los 100 caracteres")
    String storageName

) {}
