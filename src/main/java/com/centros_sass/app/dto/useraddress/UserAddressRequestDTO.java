package com.centros_sass.app.dto.useraddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserAddressRequestDTO(
    @NotNull(message = "El usuario es obligatorio")
    Integer userId,

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 100, message = "La dirección no puede exceder 100 caracteres")
    String address,

    @NotBlank(message = "El código postal es obligatorio")
    @Size(max = 10, message = "El código postal no puede exceder 10 caracteres")
    String postalCode,

    @NotNull(message = "La ciudad es obligatoria")
    Integer cityId,

    @NotNull(message = "La provincia es obligatoria")
    Integer provinceId,

    @NotNull(message = "La región es obligatoria")
    Integer regionId
) {}