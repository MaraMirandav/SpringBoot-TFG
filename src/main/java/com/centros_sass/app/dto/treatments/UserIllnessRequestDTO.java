package com.centros_sass.app.dto.treatments;

import jakarta.validation.constraints.NotNull;

public record UserIllnessRequestDTO(

    @NotNull(message = "La ficha médica es obligatoria")
    Integer userMedicalInfoId,

    @NotNull(message = "La enfermedad es obligatoria")
    Integer illnessId

) {}
