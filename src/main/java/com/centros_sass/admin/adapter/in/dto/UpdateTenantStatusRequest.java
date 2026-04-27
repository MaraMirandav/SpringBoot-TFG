package com.centros_sass.admin.adapter.in.dto;

import com.centros_sass.admin.domain.model.TenantStatus;
import jakarta.validation.constraints.NotNull;

/**
 * UpdateTenantStatusRequest — Body para cambiar el status de un tenant.
 *
 * Se usa en PATCH /api/v1/admin/tenants/{id}/status
 */
public record UpdateTenantStatusRequest(

    @NotNull(message = "El status es obligatorio")
    TenantStatus status
) {}