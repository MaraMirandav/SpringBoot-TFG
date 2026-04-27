package com.centros_sass.app.dto.catalogs.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CityRequestDTO(

    @NotBlank(message = "El ciudad es obligatorio")
    @Size(max = 100, message = "El ciudad no puede exceder los 100 caracteres")
    String cityName

) {}
