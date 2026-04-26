package com.centros_sass.app.dto.bathroom;

import java.time.LocalTime;

public record BathroomTurnResponseDTO(
    Integer id,
    String turnName,
    LocalTime startAt,
    LocalTime endAt,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
