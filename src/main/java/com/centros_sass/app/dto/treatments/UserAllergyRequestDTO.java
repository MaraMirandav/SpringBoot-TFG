package com.centros_sass.app.dto.treatments;

import java.util.Set;

import jakarta.validation.constraints.NotNull;

public record UserAllergyRequestDTO(

    @NotNull(message = "La ficha médica es obligatoria")
    Integer userMedicalInfoId,

    @NotNull(message = "La alergia es obligatoria")
    Integer allergyId,

    String comment,

    Set<Integer> medicationIds

) {}
