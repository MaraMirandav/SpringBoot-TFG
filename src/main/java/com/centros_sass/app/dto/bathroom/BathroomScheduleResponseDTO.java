package com.centros_sass.app.dto.bathroom;

public record BathroomScheduleResponseDTO(
    Integer id,
    Integer userId,
    String userFullName,
    Integer bathroomTurnId,
    String turnName,
    Integer bathroomTaskId,
    String taskName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
