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
public class ItemConditionUpdateDTO {

    @Size(max = 50, message = "El nombre de la condición no puede exceder los 50 caracteres")
    private String conditionName;

    private Boolean isActive;

}
