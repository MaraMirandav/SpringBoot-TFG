package com.centros_sass.app.dto.transport;

import java.time.LocalTime;
import java.util.Set;

import jakarta.validation.constraints.NotNull;

public record TransportRouteRequestDTO(

    @NotNull(message = "El número de ruta es obligatorio")
    Integer routeNumber,

    @NotNull(message = "La hora de inicio es obligatoria")
    LocalTime startTime,

    @NotNull(message = "La hora de fin es obligatoria")
    LocalTime endTime,

    @NotNull(message = "El turno de ruta es obligatorio")
    Integer routeShiftId,

    @NotNull(message = "El vehículo es obligatorio")
    Integer routeVehicleId,

    @NotNull(message = "El conductor es obligatorio")
    Integer driverId,

    @NotNull(message = "El copiloto es obligatorio")
    Integer copilotId

) {}
