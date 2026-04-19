package com.centros_sass.app.dto.useraddress;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressUpdateDTO {

    @Size(max = 100, message = "La dirección no puede exceder 100 caracteres")
    private String address;

    @Size(max = 10, message = "El código postal no puede exceder 10 caracteres")
    private String postalCode;

    private Integer cityId;
    private Integer provinceId;
    private Integer regionId;

    private Boolean isActive;
}