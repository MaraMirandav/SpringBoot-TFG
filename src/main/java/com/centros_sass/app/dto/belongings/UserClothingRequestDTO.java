package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotNull;

public record UserClothingRequestDTO(

    @NotNull(message = "El tipo de prenda es obligatorio")
    Integer clothingTypeId,

    @NotNull(message = "El estado de limpieza es obligatorio")
    Boolean isClean,

    @NotNull(message = "El estado de devolución es obligatorio")
    Boolean isReturned,

    @NotNull(message = "La fecha de recepción es obligatoria")
    java.time.LocalDateTime receivedAt,

    @NotNull(message = "La razón de regreso es obligatoria")
    Integer returnReasonId

) {}
