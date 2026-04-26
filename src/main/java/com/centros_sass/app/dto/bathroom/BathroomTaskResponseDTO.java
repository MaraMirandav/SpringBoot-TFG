package com.centros_sass.app.dto.bathroom;

import java.time.LocalTime;

public record BathroomTaskResponseDTO(
    Integer id,
    String taskName,
    LocalTime estimatedTime,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
