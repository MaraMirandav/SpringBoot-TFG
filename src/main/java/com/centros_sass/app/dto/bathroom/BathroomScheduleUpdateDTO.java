package com.centros_sass.app.dto.bathroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BathroomScheduleUpdateDTO {

    private Integer userId;

    private Integer bathroomTurnId;

    private Integer bathroomTaskId;

    private Boolean isActive;

}
