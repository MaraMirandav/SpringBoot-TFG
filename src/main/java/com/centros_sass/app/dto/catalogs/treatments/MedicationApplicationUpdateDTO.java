package com.centros_sass.app.dto.catalogs.treatments;

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
public class MedicationApplicationUpdateDTO {

    @Size(max = 100, message = "El aplicación de medicamento no puede exceder los 100 caracteres")
    private String medicationApplicationName;

}
