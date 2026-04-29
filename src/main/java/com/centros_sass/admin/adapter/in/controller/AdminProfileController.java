package com.centros_sass.admin.adapter.in.controller;

import com.centros_sass.admin.adapter.in.dto.AdminProfileResponse;
import com.centros_sass.admin.adapter.in.dto.UpdateAdminProfileRequest;
import com.centros_sass.admin.application.service.AdminUserService;
import com.centros_sass.app.generic.ApiDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminProfileController — endpoints para gestionar el perfil del admin logueado.
 *
 * Proporciona dos operaciones:
 *   - GET /api/v1/admin/profile: obtener los datos del perfil actual
 *   - PUT /api/v1/admin/profile: actualizar fullName y/o contraseña
 *
 * El email del admin autenticado se obtiene del SecurityContext (del JWT).
 * No es necesario pasar el email como parámetro — está en el token.
 *
 * @author SaasCon
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/admin/profile")
@RequiredArgsConstructor
public class AdminProfileController {

    /**
     * Servicio de gestión de usuarios admin.
     * Inyectado por constructor via @RequiredArgsConstructor.
     */
    private final AdminUserService adminUserService;

    /**
     * Obtiene el perfil del admin actualmente autenticado.
     *
     * El email se extrae del JWT (claim "sub") a través del SecurityContext.
     * Spring Security ya validó el token, así que confiamos en el email del token.
     *
     * @return ResponseEntity con ApiDataResponse conteniendo el perfil del admin
     */
    @GetMapping
    public ResponseEntity<ApiDataResponse<AdminProfileResponse>> getProfile() {
        // Extraer el email (username) del token JWT desde el SecurityContext
        // El token ya fue validado por Spring Security en el filtro anterior
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Obtener el perfil desde el servicio
        AdminProfileResponse response = adminUserService.getProfile(email);

        // Retornar respuesta exitosa con código 200
        return ResponseEntity.ok(
                new ApiDataResponse<>("Perfil del usuario", response, 200)
        );
    }

    /**
     * Actualiza el perfil del admin autenticado.
     *
     * Permite cambiar:
     *   - fullName: nombre completo (opcional)
     *   - newPassword: nueva contraseña (opcional, requiere currentPassword)
     *
     * La contraseña actual (currentPassword) es OBLIGATORIA para autorizar el cambio.
     * Esto previene que alguien con acceso al navegador pueda cambiar la contraseña
     * sin conocer la actual.
     *
     * @param request DTO con los datos a actualizar
     * @return ResponseEntity con ApiDataResponse conteniendo el perfil actualizado
     */
    @PutMapping
    public ResponseEntity<ApiDataResponse<AdminProfileResponse>> updateProfile(
            @Valid @RequestBody UpdateAdminProfileRequest request) {

        // Extraer el email del token JWT
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Actualizar el perfil a través del servicio
        AdminProfileResponse response = adminUserService.updateProfile(email, request);

        // Retornar respuesta exitosa con código 200
        return ResponseEntity.ok(
                new ApiDataResponse<>("Perfil actualizado correctamente", response, 200)
        );
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────
// 1. SecurityContextHolder: punto de acceso al usuario autenticado
//    getAuthentication().getName() devuelve el "sub" del JWT (el email)
// 2. Patrón Adapter en Controllers: el controller es "adaptador" entre
//    el protocolo HTTP y la lógica de negocio (servicio)
// 3. @Valid en @RequestBody: activa la validación de Bean Validation
//    defined en el record DTO (notas @Size, @NotBlank, etc)
// 4. @Transactional en el servicio: la escritura a BD requiere transacción
//    El controller NO tiene @Transactional — esa responsabilidad es del servicio
// 5. Omitir información sensible: nunca devolvemos passwordHash en respuestas
//    Creamos AdminProfileResponse que excluye ese campo por diseño
// ─────────────────────────────────────────────────────────────