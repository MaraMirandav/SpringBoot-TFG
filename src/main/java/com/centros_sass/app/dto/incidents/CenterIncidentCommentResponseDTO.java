package com.centros_sass.app.dto.incidents;

public record CenterIncidentCommentResponseDTO(

    Integer id,
    Integer workerId,
    String workerFullName,
    String comment,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
