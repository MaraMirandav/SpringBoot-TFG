package com.centros_sass.app.dto.catalogs.address;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceUpdateDTO {

    @Size(max = 100, message = "El provincia no puede exceder los 100 caracteres")
    private String provinceName;

}
