package com.centros_sass.app.dto.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIllnessUpdateDTO {

    private Integer userMedicalInfoId;

    private Integer illnessId;

    private Boolean isActive;

}
