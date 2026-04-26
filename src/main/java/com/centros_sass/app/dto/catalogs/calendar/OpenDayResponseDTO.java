package com.centros_sass.app.dto.catalogs.calendar;

import java.time.LocalTime;

public record OpenDayResponseDTO(
    Integer id,
    String dayName,
    LocalTime openAt,
    LocalTime closeAt,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
