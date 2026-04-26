package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotNull;

public record UserBelongingRequestDTO(

    @NotNull(message = "El usuario es obligatorio")
    Integer userId,

    @NotNull(message = "El trabajador es obligatorio")
    Integer workerId,

    Integer userClothingId,

    Integer userDiaperId,

    Integer userObjectId,

    @NotNull(message = "El indicador de petición es obligatorio")
    Boolean isRequest,

    String comment

) {}
