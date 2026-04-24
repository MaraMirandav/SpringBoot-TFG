package com.centros_sass.app.dto.bathroom;

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
public class BathroomTurnUpdateDTO {

    @Size(max = 50, message = "El nombre del turno no puede exceder los 50 caracteres")
    private String turnName;

    private LocalTime startAt;

    private LocalTime endAt;

}
