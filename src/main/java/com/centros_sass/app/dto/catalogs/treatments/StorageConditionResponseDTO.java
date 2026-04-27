package com.centros_sass.app.dto.catalogs.treatments;

public record StorageConditionResponseDTO(
    Integer id,
    String storageName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
