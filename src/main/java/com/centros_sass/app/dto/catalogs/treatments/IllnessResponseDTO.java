package com.centros_sass.app.dto.catalogs.treatments;

public record IllnessResponseDTO(
    Integer id,
    String illnessName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
