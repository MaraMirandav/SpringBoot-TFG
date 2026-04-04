package com.centros_sass.app.dto.worker;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear un trabajador en el sistema.
 *
 * Utiliza Java Record para inmutabilidad y reducción de boilerplate.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record WorkerRequestDTO(
    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(max = 25, message = "El primer nombre no puede exceder los 25 caracteres")
    String firstName,

    @Size(max = 25, message = "El segundo nombre no puede exceder los 25 caracteres")
    String secondName,

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(max = 25, message = "El primer apellido no puede exceder los 25 caracteres")
    String firstSurname,

    @Size(max = 25, message = "El segundo apellido no puede exceder los 25 caracteres")
    String secondSurname,

    @NotBlank(message = "El DNI es obligatorio")
    @Size(max = 9, message = "El DNI no puede exceder los 9 caracteres")
    String dni,

    @NotBlank(message = "El teléfono principal es obligatorio")
    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    String mainPhone,

    @Size(max = 20, message = "El teléfono secundario no puede exceder los 20 caracteres")
    String secondPhone,

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es valido")
    @Size(max = 255, message = "El email no puede exceder los 255 caracteres")
    String email,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String password,

    Set<Integer> roleIds
) {}
