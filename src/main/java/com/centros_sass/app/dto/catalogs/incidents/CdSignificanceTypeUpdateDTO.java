package com.centros_sass.app.dto.catalogs.incidents;

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
public class CdSignificanceTypeUpdateDTO {

    @Size(max = 50, message = "El tipo de significancia del centro no puede exceder los 50 caracteres")
    private String significanceName;

}
