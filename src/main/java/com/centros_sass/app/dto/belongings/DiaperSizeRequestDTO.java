package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear un tamaño de pañal en el sistema.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record DiaperSizeRequestDTO(

    @NotBlank(message = "El tamaño de pañal es obligatorio")
    @Size(max = 20, message = "El tamaño de pañal no puede exceder los 20 caracteres")
    String size

) {}
