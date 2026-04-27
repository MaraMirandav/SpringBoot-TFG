package com.centros_sass.app.dto.treatments;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicationRequestDTO(

    @NotNull(message = "El usuario es obligatorio")
    Integer userId,

    @NotNull(message = "El nombre del medicamento es obligatorio")
    Integer medicationNameId,

    @NotBlank(message = "La dosis es obligatoria")
    @Size(max = 100, message = "La dosis no puede exceder los 100 caracteres")
    String dose,

    @NotNull(message = "La fecha de recepción es obligatoria")
    LocalDate receptionDate,

    @NotNull(message = "La fecha de caducidad es obligatoria")
    LocalDate expirationDate,

    @NotNull(message = "La condición de almacenamiento es obligatoria")
    Integer storageConditionId,

    @NotNull(message = "La vía de administración es obligatoria")
    Integer medicationApplicationId,

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    Integer stock

) {}
