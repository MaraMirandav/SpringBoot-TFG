package com.centros_sass.app.dto.userattendancerecord;

import java.time.LocalTime;

public record UserAttendanceRecordResponseDTO(

        Integer id,
        Integer userId,
        String userFullName,
        Integer attendanceDayId,
        String dayName,
        LocalTime startAt,
        LocalTime endAt,
        Boolean isPresent,
        String createdAt,
        String createdBy,
        String updatedAt,
        String updatedBy

) {}
