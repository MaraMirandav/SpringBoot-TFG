package com.centros_sass.app.dto.catalogs.address;

public record RegionResponseDTO(
    Integer id,
    String regionName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
