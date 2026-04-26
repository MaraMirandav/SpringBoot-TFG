package com.centros_sass.app.dto.user;

import java.time.LocalDate;

public record UserResponseDTO(
    Integer id,
    String firstName,
    String secondName,
    String firstSurname,
    String secondSurname,
    String alias,
    String email,
    String phone,
    String cellphone,
    String dni,
    String sexName,
    LocalDate birthDate,
    String dependencyName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
