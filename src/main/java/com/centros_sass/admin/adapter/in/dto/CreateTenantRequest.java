package com.centros_sass.admin.adapter.in.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * CreateTenantRequest — Datos para crear un nuevo tenant.
 *
 * Este DTO se recibe en el endpoint POST /api/v1/admin/tenants.
 * Validaciones:
 *   - name: requerido, max 100 caracteres
 *   - slug: requerido, formato [a-z0-9-], 3-50 caracteres
 *   - adminEmail: requerido, formato válido de email
 *   - planSlug: requerido, formato alfanumérico
 */
public record CreateTenantRequest(

    @NotBlank(message = "El nombre del tenant es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    String name,

    @NotBlank(message = "El slug es obligatorio")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "El slug debe contener solo minúsculas, números y guiones")
    @Size(min = 3, max = 50, message = "El slug debe tener entre 3 y 50 caracteres")
    String slug,

    @NotBlank(message = "El email del administrador es obligatorio")
    @Email(message = "Debe ingresar un correo electrónico válido")
    String adminEmail,

    @NotBlank(message = "El plan es obligatorio")
    @Pattern(regexp = "^[a-z0-9]+$", message = "El plan debe contener solo minúsculas y números")
    String planSlug,

    @NotBlank(message = "El email del director es obligatorio")
    @Email(message = "Debe ingresa un correo electrónico válido")
    String directorEmail,

    @NotBlank(message = "La contraseña del director es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String directorPassword,

    @NotBlank(message = "El nombre del director es obligatorio")
    @Size(max = 25, message = "El nombre no puede exceder 25 caracteres")
    String directorFirstName,

    @NotBlank(message = "El apellido del director es obligatorio")
    @Size(max = 25, message = "El apellido no puede exceder 25 caracteres")
    String directorFirstSurname,

    @NotBlank(message = "El DNI del director es obligatorio")
    @Size(min = 9, max = 9, message = "El DNI debe tener exactamente 9 caracteres")
    String directorDni,

    @NotBlank(message = "El teléfono del director es obligatorio")
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    String directorPhone
) {}