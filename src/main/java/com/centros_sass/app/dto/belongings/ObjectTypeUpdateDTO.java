package com.centros_sass.app.dto.belongings;

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
public class ObjectTypeUpdateDTO {

    @Size(max = 50, message = "El nombre del objeto no puede exceder los 50 caracteres")
    private String objectName;

    private Boolean isActive;

}
