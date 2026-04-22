package com.centros_sass.app.dto.vehicle;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteVehicleUpdateDTO {

    @Size(max = 10, message = "La matrícula no puede exceder los 10 caracteres")
    private String licensePlate;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacity;

    private Boolean hasWheelchair;

    private Integer wheelchairCapacity;

    private Boolean isActive;

}
