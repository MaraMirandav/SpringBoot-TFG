package com.centros_sass.app.dto.treatments;

import java.time.LocalDate;
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
public class TreatmentDetailUpdateDTO {

    private LocalDate startDate;

    private LocalDate endDate;

    private String comment;

    private Set<Integer> medicationIds;

    private Set<Integer> userIllnessIds;

    private Boolean isActive;

}
