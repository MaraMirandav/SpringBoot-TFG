package com.centros_sass.app.dto.catalogs.treatments;

public record MedicationApplicationResponseDTO(
    Integer id,
    String medicationApplicationName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
