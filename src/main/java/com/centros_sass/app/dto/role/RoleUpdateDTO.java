package com.centros_sass.app.dto.role;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de actualización para modificar un rol existente.
 * 
 * Utiliza clase con Lombok ya que los campos son opcionales.
 * 
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleUpdateDTO {

    @Size(max = 50, message = "El nombre del rol no puede exceder 50 caracteres")
    private String roleName;

}