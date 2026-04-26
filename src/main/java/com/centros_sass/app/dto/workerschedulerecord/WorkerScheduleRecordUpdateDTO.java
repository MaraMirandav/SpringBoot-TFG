package com.centros_sass.app.dto.workerschedulerecord;

import java.time.LocalDateTime;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerScheduleRecordUpdateDTO {

    @PastOrPresent(message = "La hora de entrada no puede ser futura")
    private LocalDateTime clockIn;

    @PastOrPresent(message = "La hora de salida no puede ser futura")
    private LocalDateTime clockOut;

    private Boolean isActive;

}
