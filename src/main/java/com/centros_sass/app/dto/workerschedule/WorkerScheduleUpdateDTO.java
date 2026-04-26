package com.centros_sass.app.dto.workerschedule;

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
public class WorkerScheduleUpdateDTO {

    private Integer openDayId;

    private LocalTime startAt;

    private LocalTime endAt;

    private Boolean isActive;

}
