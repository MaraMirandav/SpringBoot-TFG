package com.centros_sass.app.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debes ingresar un correo electrónico válido")
    String email,

    @NotBlank(message = "El identificador del centro es obligatorio")
    String tenantId,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String password,

    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(max = 255, message = "El primer nombre no puede exceder los 255 caracteres")
    String firstName,

    String secondName,

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(max = 255, message = "El primer apellido no puede exceder los 255 caracteres")
    String firstSurname,

    String secondSurname,

    @NotBlank(message = "El DNI es obligatorio")
    @Size(max = 20, message = "El DNI no puede exceder los 20 caracteres")
    String dni,

    @NotBlank(message = "El teléfono principal es obligatorio")
    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    String mainPhone,

    String secondPhone
) {}
