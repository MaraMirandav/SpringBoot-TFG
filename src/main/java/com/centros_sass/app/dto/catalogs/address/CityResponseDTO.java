package com.centros_sass.app.dto.catalogs.address;

public record CityResponseDTO(
    Integer id,
    String cityName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
