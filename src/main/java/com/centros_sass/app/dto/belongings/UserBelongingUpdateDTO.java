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
public class UserBelongingUpdateDTO {

    private Integer userId;

    private Integer workerId;

    private Integer userClothingId;

    private Integer userDiaperId;

    private Integer userObjectId;

    private Boolean isRequest;

    private String comment;

    private Boolean isActive;

}
