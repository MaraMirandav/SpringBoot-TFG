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
public class ClothingTypeUpdateDTO {

    @Size(max = 50, message = "El nombre de la prenda no puede exceder los 50 caracteres")
    private String clothingName;


}
