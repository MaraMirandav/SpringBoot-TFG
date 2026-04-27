package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotNull;

public record UserObjectRequestDTO(

    @NotNull(message = "El tipo de objeto es obligatorio")
    Integer objectTypeId,

    @NotNull(message = "La condición del objeto es obligatoria")
    Integer itemConditionId,

    String comment

) {}
