package com.centros_sass.app.dto.catalogs.organization;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleUpdateDTO {

    @Size(max = 50, message = "El nombre del rol no puede exceder 50 caracteres")
    private String roleName;

}