package com.centros_sass.app.dto.userattendanceday;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAttendanceDayUpdateDTO {

    private Integer dayId;

    private LocalTime startAt;

    private LocalTime endAt;

    private Boolean isActive;

}
