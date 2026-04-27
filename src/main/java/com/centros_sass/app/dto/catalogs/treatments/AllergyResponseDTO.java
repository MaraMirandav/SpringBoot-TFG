package com.centros_sass.app.dto.catalogs.treatments;

public record AllergyResponseDTO(
    Integer id,
    String allergyName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
