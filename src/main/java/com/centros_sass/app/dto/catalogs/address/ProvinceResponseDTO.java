package com.centros_sass.app.dto.catalogs.address;

public record ProvinceResponseDTO(
    Integer id,
    String provinceName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
