package com.centros_sass.app.dto.catalogs.organization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequestDTO(
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre del rol no puede exceder 50 caracteres")
    String roleName
) {}