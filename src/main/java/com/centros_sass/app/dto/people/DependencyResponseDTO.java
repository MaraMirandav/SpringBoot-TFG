package com.centros_sass.app.dto.people;

public record DependencyResponseDTO(
    Integer id,
    String dependencyLevel,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
