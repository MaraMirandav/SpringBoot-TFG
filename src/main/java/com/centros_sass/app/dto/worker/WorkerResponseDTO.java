package com.centros_sass.app.dto.worker;

import java.util.Set;

public record WorkerResponseDTO(
    Integer id,
    String firstName,
    String secondName,
    String firstSurname,
    String secondSurname,
    String dni,
    String mainPhone,
    String secondPhone,
    String email,
    Boolean isActive,
    Set<Integer> roleIds,
    Integer scheduleCount,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
