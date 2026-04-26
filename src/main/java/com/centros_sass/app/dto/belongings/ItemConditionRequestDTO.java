package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear una condición de objeto en el sistema.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record ItemConditionRequestDTO(

    @NotBlank(message = "El nombre de la condición es obligatorio")
    @Size(max = 50, message = "El nombre de la condición no puede exceder los 50 caracteres")
    String conditionName

) {}
