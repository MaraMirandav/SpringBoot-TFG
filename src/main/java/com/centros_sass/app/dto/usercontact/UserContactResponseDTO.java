package com.centros_sass.app.dto.usercontact;

public record UserContactResponseDTO(
    Integer id,
    Integer userId,
    String userFullName,
    String contactName,
    Integer relationshipId,
    String relationshipName,
    String contactPhone,
    String contactEmail,
    Boolean isContactMain,
    String contactNote,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}