package com.centros_sass.app.dto.treatments;

public record UserMedicalInfoResponseDTO(

    Integer id,
    Integer userId,
    String userFullName,
    Integer workerId,
    String workerFullName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
