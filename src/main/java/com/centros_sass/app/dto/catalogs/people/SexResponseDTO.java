package com.centros_sass.app.dto.catalogs.people;

public record SexResponseDTO(
    Integer id,
    String sex,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
