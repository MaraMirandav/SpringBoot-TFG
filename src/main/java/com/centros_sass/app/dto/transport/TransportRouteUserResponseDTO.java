package com.centros_sass.app.dto.transport;

import java.time.LocalTime;

public record TransportRouteUserResponseDTO(

    Integer userId,
    String userFullName,
    LocalTime arrivalTime,
    Boolean usesWheelchair,
    String comment,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
