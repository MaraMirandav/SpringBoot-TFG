package com.centros_sass.app.dto.belongings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserClothingUpdateDTO {

    private Integer clothingTypeId;

    private Boolean isClean;

    private Boolean isReturned;

    private LocalDateTime receivedAt;

    private LocalDateTime returnedAt;

    private Integer returnReasonId;

    private Boolean isActive;

}
