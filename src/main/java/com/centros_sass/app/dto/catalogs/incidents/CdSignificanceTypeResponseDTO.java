package com.centros_sass.app.dto.catalogs.incidents;

public record CdSignificanceTypeResponseDTO(
    Integer id,
    String significanceName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
