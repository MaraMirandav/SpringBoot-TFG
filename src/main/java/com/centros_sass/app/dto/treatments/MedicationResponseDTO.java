package com.centros_sass.app.dto.treatments;

import java.time.LocalDate;

public record MedicationResponseDTO(

    Integer id,
    Integer userId,
    String userFullName,
    Integer medicationNameId,
    String medicationName,
    String dose,
    LocalDate receptionDate,
    LocalDate expirationDate,
    Integer storageConditionId,
    String storageConditionName,
    Integer medicationApplicationId,
    String medicationApplicationName,
    Integer stock,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
