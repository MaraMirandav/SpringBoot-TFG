package com.centros_sass.app.dto.catalogs.belongings;

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
public class ReturnReasonUpdateDTO {

    @Size(max = 50, message = "La razón de devolución no puede exceder los 50 caracteres")
    private String reason;


}
