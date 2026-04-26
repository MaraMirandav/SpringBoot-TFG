package com.centros_sass.app.dto.catalogs.belongings;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaperSizeUpdateDTO {

    @Size(max = 20, message = "El tamaño de pañal no puede exceder los 20 caracteres")
    private String size;


}
