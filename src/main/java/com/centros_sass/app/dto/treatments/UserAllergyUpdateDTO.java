package com.centros_sass.app.dto.treatments;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAllergyUpdateDTO {

    private Integer userMedicalInfoId;

    private Integer allergyId;

    private String comment;

    private Set<Integer> medicationIds;

    private Boolean isActive;

}
