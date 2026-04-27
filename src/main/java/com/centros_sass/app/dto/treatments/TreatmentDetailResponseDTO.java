package com.centros_sass.app.dto.treatments;

import java.time.LocalDate;
import java.util.Set;

public record TreatmentDetailResponseDTO(

    Integer id,
    LocalDate startDate,
    LocalDate endDate,
    String comment,
    Set<Integer> medicationIds,
    Set<Integer> userIllnessIds,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
