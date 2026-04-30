package com.centros_sass.customer.adapter.in.dto;

import jakarta.validation.constraints.*;

public record CustomerRegisterRequest(
    @NotBlank @Size(max = 100) String centerName,
    @NotBlank @Pattern(regexp = "^[a-z0-9-]+$") @Size(min = 3, max = 50) String slug,
    @NotBlank String planSlug,
    @NotBlank @Email String directorEmail,
    @NotBlank @Size(min = 8, max = 72) String directorPassword,
    @NotBlank @Size(max = 25) String directorFirstName,
    @NotBlank @Size(max = 25) String directorFirstSurname,
    @NotBlank @Size(min = 9, max = 9) String directorDni,
    @NotBlank @Size(max = 20) String directorPhone
) {}