package com.centros_sass.app.dto.catalogs.treatments;

public record MedicationNameResponseDTO(
    Integer id,
    String medicationName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
