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
public class UserMedicalInfoUpdateDTO {

    private Integer userId;

    private Integer workerId;

    private Boolean isActive;

}
