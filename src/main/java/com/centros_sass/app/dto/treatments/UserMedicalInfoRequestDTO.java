package com.centros_sass.app.dto.treatments;

import jakarta.validation.constraints.NotNull;

public record UserMedicalInfoRequestDTO(

    @NotNull(message = "El usuario es obligatorio")
    Integer userId,

    @NotNull(message = "El trabajador es obligatorio")
    Integer workerId

) {}
