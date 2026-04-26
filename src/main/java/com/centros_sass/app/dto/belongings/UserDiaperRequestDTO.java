package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotNull;

public record UserDiaperRequestDTO(

    @NotNull(message = "La talla de pañal es obligatoria")
    Integer diaperSizeId,

    @NotNull(message = "El tipo de pañal es obligatorio")
    Integer diaperTypeId,

    @NotNull(message = "La cantidad es obligatoria")
    Integer quantity

) {}
