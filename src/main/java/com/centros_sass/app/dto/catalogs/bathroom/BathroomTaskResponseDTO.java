package com.centros_sass.app.dto.catalogs.bathroom;

import java.time.LocalTime;

public record BathroomTaskResponseDTO(
    Integer id,
    String taskName,
    LocalTime estimatedTime,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
