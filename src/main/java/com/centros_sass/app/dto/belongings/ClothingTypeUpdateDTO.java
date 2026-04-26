package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de actualización para modificar un tipo de prenda existente.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothingTypeUpdateDTO {

    @Size(max = 50, message = "El nombre de la prenda no puede exceder los 50 caracteres")
    private String clothingName;

    private Boolean isActive;

}
