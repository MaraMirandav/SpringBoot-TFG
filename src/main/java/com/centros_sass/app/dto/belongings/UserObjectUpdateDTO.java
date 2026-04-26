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
public class UserObjectUpdateDTO {

    private Integer objectTypeId;

    private Integer itemConditionId;

    private String comment;

    private Boolean isActive;

}
