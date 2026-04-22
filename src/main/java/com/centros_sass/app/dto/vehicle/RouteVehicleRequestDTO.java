package com.centros_sass.app.dto.vehicle;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RouteVehicleRequestDTO(

    @NotBlank(message = "La matrícula es obligatoria")
    @Size(max = 10, message = "La matrícula no puede exceder los 10 caracteres")
    String licensePlate,

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    Integer capacity,

    @NotNull(message = "Debe indicar si tiene silla de ruedas")
    Boolean hasWheelchair,

    Integer wheelchairCapacity

) {}
