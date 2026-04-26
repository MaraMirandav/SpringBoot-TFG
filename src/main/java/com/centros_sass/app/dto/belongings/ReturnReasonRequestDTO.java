package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear una razón de devolución en el sistema.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record ReturnReasonRequestDTO(

    @NotBlank(message = "La razón de devolución es obligatoria")
    @Size(max = 50, message = "La razón de devolución no puede exceder los 50 caracteres")
    String reason

) {}
