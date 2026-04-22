package com.centros_sass.app.dto.userattendanceday;

import java.time.LocalTime;

public record UserAttendanceDayResponseDTO(

    Integer id,
    Integer userId,
    String userFullName,
    Integer dayId,
    String dayName,
    LocalTime startAt,
    LocalTime endAt,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
