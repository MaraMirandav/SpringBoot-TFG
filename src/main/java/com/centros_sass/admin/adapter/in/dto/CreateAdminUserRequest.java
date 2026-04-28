package com.centros_sass.admin.adapter.in.dto;

import com.centros_sass.admin.domain.model.AdminUserRole;
import jakarta.validation.constraints.*;

public record CreateAdminUserRequest(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    String email,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String password,

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    String fullName,

    @NotNull(message = "El rol es obligatorio")
    AdminUserRole role
) {}