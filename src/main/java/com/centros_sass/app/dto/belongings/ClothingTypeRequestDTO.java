package com.centros_sass.app.dto.belongings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClothingTypeRequestDTO(

    @NotBlank(message = "El nombre de la prenda es obligatorio")
    @Size(max = 50, message = "El nombre de la prenda no puede exceder los 50 caracteres")
    String clothingName

) {}
