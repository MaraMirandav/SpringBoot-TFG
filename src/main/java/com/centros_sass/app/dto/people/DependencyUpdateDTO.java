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
public class DependencyUpdateDTO {

    @Size(max = 50, message = "El nivel de dependencia no puede exceder los 50 caracteres")
    private String dependencyLevel;

}
