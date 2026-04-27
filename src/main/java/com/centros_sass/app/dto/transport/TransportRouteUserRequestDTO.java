package com.centros_sass.app.dto.transport;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record TransportRouteUserRequestDTO(

    @NotNull(message = "El usuario es obligatorio")
    Integer userId,

    LocalTime arrivalTime,

    Boolean usesWheelchair,

    String comment

) {}
