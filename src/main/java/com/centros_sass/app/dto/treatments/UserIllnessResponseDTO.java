package com.centros_sass.app.dto.treatments;

public record UserIllnessResponseDTO(

    Integer id,
    Integer userMedicalInfoId,
    String userFullName,
    Integer illnessId,
    String illnessName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
