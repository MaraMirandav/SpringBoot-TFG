package com.centros_sass.app.dto.transport;

import java.util.Set;

public record TransportRouteResponseDTO(

    Integer id,
    Integer routeNumber,
    String startTime,
    String endTime,
    Integer routeShiftId,
    String routeShiftName,
    Integer routeVehicleId,
    String licensePlate,
    Integer driverId,
    String driverFullName,
    Integer copilotId,
    String copilotFullName,
    Boolean isActive,
    Set<Integer> userIds,
    Integer userCount,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
