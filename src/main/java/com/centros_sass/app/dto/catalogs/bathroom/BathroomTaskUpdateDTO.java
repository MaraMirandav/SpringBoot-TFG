package com.centros_sass.app.dto.catalogs.bathroom;

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
public class BathroomTaskUpdateDTO {

    @Size(max = 50, message = "El nombre de la tarea no puede exceder los 50 caracteres")
    private String taskName;

    private LocalTime estimatedTime;


}
