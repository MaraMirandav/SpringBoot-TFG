package com.centros_sass.app.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear un rol en el sistema.
 * 
 * Utiliza Java Record para inmutabilidad y reducción de boilerplate.
 * 
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record RoleRequestDTO(
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre del rol no puede exceder 50 caracteres")
    String roleName
) {}