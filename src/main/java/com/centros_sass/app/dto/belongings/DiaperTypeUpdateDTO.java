package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de actualización para modificar un tipo de pañal existente.
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
public class DiaperTypeUpdateDTO {

    @Size(max = 50, message = "El tipo de pañal no puede exceder los 50 caracteres")
    private String type;

    private Boolean isActive;

}
