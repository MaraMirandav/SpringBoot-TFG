package com.centros_sass.app.dto.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear un usuario (adulto mayor) en el sistema.
 *
 * Utiliza Java Record para inmutabilidad y reducción de boilerplate.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserRequestDTO(
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

    @Size(max = 25, message = "El alias no puede exceder los 25 caracteres")
    String alias,

    @Email(message = "El email no es válido")
    @Size(max = 255, message = "El email no puede exceder los 255 caracteres")
    String email,

    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    String phone,

    @Size(max = 20, message = "El móvil no puede exceder los 20 caracteres")
    String cellphone,

    @NotNull(message = "El DNI/NIE es obligatorio")
    @Size(max = 9, message = "El DNI/NIE no puede exceder los 9 caracteres")
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]$|^[XYZxyz][0-9]{7}[A-Za-z]$",
             message = "El DNI/NIE es inválido. Formato esperado: 12345678A o X1234567A")
    String dni,

    @NotNull(message = "El sexo es obligatorio")
    Integer sexId,

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    LocalDate birthDate,

    @NotNull(message = "El nivel de dependencia es obligatorio")
    Integer dependencyId
) {}
