package com.centros_sass.admin.adapter.in.dto;

import com.centros_sass.admin.domain.model.AdminUserStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateAdminUserStatusRequest(
    @NotNull(message = "El estado es obligatorio")
    AdminUserStatus status
) {}