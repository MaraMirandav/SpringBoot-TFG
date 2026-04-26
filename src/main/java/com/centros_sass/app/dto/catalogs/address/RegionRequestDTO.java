package com.centros_sass.app.dto.catalogs.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegionRequestDTO(

    @NotBlank(message = "El región es obligatorio")
    @Size(max = 100, message = "El región no puede exceder los 100 caracteres")
    String regionName

) {}
