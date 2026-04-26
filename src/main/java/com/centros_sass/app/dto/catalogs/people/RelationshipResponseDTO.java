package com.centros_sass.app.dto.catalogs.people;

public record RelationshipResponseDTO(
    Integer id,
    String relationshipName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
