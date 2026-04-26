package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear un tipo de objeto en el sistema.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record ObjectTypeRequestDTO(

    @NotBlank(message = "El nombre del objeto es obligatorio")
    @Size(max = 50, message = "El nombre del objeto no puede exceder los 50 caracteres")
    String objectName

) {}
