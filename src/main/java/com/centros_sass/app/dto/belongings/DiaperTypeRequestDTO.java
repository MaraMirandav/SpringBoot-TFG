package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear un tipo de pañal en el sistema.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record DiaperTypeRequestDTO(

    @NotBlank(message = "El tipo de pañal es obligatorio")
    @Size(max = 50, message = "El tipo de pañal no puede exceder los 50 caracteres")
    String type

) {}
