package com.centros_sass.app.dto.usercontact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserContactRequestDTO(
    @NotNull(message = "El usuario es obligatorio")
    Integer userId,

    @NotBlank(message = "El nombre del contacto es obligatorio")
    @Size(max = 25, message = "El nombre no puede exceder 25 caracteres")
    String contactName,

    @NotNull(message = "La relación es obligatoria")
    Integer contactRelationshipId,

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    String contactPhone,

    @Email(message = "El email no es válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    String contactEmail,

    Boolean isContactMain,

    String contactNote
) {}