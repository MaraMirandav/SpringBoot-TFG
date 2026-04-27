package com.centros_sass.app.dto.catalogs.calendar;

import java.time.LocalTime;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenDayUpdateDTO {

    @Size(max = 20, message = "El día no puede exceder los 20 caracteres")
    private String dayName;

    private LocalTime openAt;

    private LocalTime closeAt;

}
