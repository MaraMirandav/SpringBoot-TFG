package com.centros_sass.app.dto.belongings;

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
public class UserDiaperUpdateDTO {

    private Integer diaperSizeId;

    private Integer diaperTypeId;

    private Integer quantity;

    private Boolean isActive;

}
