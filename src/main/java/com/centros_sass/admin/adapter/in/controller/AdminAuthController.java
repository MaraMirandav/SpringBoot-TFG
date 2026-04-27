package com.centros_sass.admin.adapter.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.admin.application.AdminAuthService;
import com.centros_sass.app.dto.auth.AuthResponse;
import com.centros_sass.app.dto.auth.LoginRequest;
import com.centros_sass.app.generic.ApiDataResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * AdminAuthController — endpoint para autenticación de administradores internos.
 *
 * Maneja el login de empleados de SaasCon que acceden al panel de administración.
 * A diferencia de AuthController (app), este opera sobre la tabla admin_users
 * en el schema "public" y no requiere tenant context.
 *
 * Rutas públicas: NO requiere autenticación JWT para acceder a /login.
 * El token JWT se genera tras verificar credenciales.
 */
@RestController
@RequestMapping("/api/v1/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    /**
     * Login de administrador interno.
     *
     * Autentica un empleado de SaasCon que accede al panel de administración.
     * Verifica email y contraseña contra la tabla admin_users (schema public).
     *
     * Ruta PÚBLICA — cualquier usuario puede intentar login.
     * Spring Security maneja la autenticación posterior con el token generado.
     *
     * @param request LoginRequest con email y password
     * @return AuthResponse con el token JWT generado
     */
    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = adminAuthService.login(request);
        return ResponseEntity.ok(new ApiDataResponse<>("Login exitoso", response, 200));
    }
}