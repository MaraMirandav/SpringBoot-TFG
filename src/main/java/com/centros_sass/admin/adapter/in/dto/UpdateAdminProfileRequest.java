package com.centros_sass.admin.adapter.in.dto;

import jakarta.validation.constraints.Size;

/**
 * UpdateAdminProfileRequest — DTO de request para actualizar el perfil del admin.
 *
 * Se usa en el endpoint PUT /api/v1/admin/profile.
 * Permite actualizar el nombre completo y/o la contraseña.
 *
 * Notas sobre contraseñas:
 *   - Si currentPassword y newPassword son null → solo actualizar fullName
 *   - Si ambos tienen valor → verificar currentPassword y actualizar contraseña
 *   - Si solo uno tiene valor → error 400 "Debes enviar ambas contraseñas"
 *
 * @author SaasCon
 * @since 1.0
 */
public record UpdateAdminProfileRequest(

    /**
     * Nuevo nombre completo del administrador.
     * Opcional — si no se envía, no se modifica.
     * Máximo 150 caracteres.
     */
    @Size(max = 150, message = "El nombre debe tener como máximo 150 caracteres")
    String fullName,

    /**
     * Contraseña actual del admin.
     * Opcional — si es null, no se cambiará la contraseña.
     * Si se envía, newPassword también debe enviarse.
     * Debe tener entre 8 y 72 caracteres (límite de BCrypt).
     */
    @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
    String currentPassword,

    /**
     * Nueva contraseña que reemplazará la actual.
     * Opcional — si es null, no se cambiará la contraseña.
     * Si se envía, currentPassword también debe enviarse.
     * Debe tener entre 8 y 72 caracteres (límite de BCrypt).
     * Se hashea con BCrypt antes de guardar en la BD.
     */
    @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
    String newPassword
) {}