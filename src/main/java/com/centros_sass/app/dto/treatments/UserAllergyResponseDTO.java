package com.centros_sass.app.dto.treatments;

import java.util.Set;

public record UserAllergyResponseDTO(

    Integer id,
    Integer userMedicalInfoId,
    String userFullName,
    Integer allergyId,
    String allergyName,
    String comment,
    Set<Integer> medicationIds,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
