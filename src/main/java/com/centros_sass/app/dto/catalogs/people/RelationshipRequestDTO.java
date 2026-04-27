package com.centros_sass.app.dto.catalogs.people;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RelationshipRequestDTO(

    @NotBlank(message = "El nombre de la relación es obligatorio")
    @Size(max = 50, message = "El nombre de la relación no puede exceder los 50 caracteres")
    String relationshipName

) {}
