package com.centros_sass.app.dto.vehicle;

public record RouteVehicleResponseDTO(

    Integer id,
    String licensePlate,
    Integer capacity,
    Boolean hasWheelchair,
    Integer wheelchairCapacity,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
