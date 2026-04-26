package com.centros_sass.app.dto.people;

public record SexResponseDTO(
    Integer id,
    String sex,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
