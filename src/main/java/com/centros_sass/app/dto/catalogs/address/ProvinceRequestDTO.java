package com.centros_sass.app.dto.catalogs.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProvinceRequestDTO(

    @NotBlank(message = "El provincia es obligatorio")
    @Size(max = 100, message = "El provincia no puede exceder los 100 caracteres")
    String provinceName

) {}
