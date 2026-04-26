package com.centros_sass.app.dto.people;

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
public class SexUpdateDTO {

    @Size(max = 20, message = "El sexo no puede exceder los 20 caracteres")
    private String sex;

}
