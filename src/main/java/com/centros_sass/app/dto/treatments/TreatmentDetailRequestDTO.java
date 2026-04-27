package com.centros_sass.app.dto.treatments;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotNull;

public record TreatmentDetailRequestDTO(

    @NotNull(message = "La fecha de inicio es obligatoria")
    LocalDate startDate,

    LocalDate endDate,

    String comment,

    Set<Integer> medicationIds,

    Set<Integer> userIllnessIds

) {}
