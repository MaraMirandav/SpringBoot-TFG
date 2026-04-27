package com.centros_sass.admin.application;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.admin.domain.repository.AdminUserRepository;
import com.centros_sass.app.dto.auth.AuthResponse;
import com.centros_sass.app.dto.auth.LoginRequest;
import com.centros_sass.core.security.AdminUserSecurity;
import com.centros_sass.core.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AdminAuthServiceImpl — implementación de autenticación para administradores internos.
 *
 * Maneja el login de empleados de SaasCon que acceden al panel de administración.
 * Opera sobre la tabla admin_users en schema "public" (no requiere tenant context).
 *
 * El flujo es:
 *   1. Buscar admin por email (si NO existe, lanza excepción genérica)
 *   2. Verificar contraseña con PasswordEncoder.matches()
 *   3. Si válida, generar token JWT con generateAdminToken()
 *   4. Devolver AuthResponse con el token
 *
 * SEGURIDAD: el mismo mensaje de error para email incorrecto y password incorrecto.
 * Esto evita que un atacante pueda enumerar qué emails existen en el sistema.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Autentica un administrador interno.
     *
     * Busca el admin por email, verifica la contraseña, y si es correcto
     * genera un JWT token para el panel admin.
     *
     * IMPORTANTE: por seguridad, retorna el MISMO mensaje de error ("Credenciales inválidas")
     * si el email no existe O si la contraseña es incorrecta.
     * Esto evita enumeration attacks.
     *
     * @param request LoginRequest con email y password
     * @return AuthResponse con el JWT token
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        String email = request.email();
        String password = request.password();

        AdminUserSecurity adminUser = adminUserRepository.findByEmail(email)
                .map(AdminUserSecurity::new)
                .orElseThrow(() -> {
                    // Seguridad: mismo mensaje para "email no existe" y "password incorrecto"
                    log.warn("Intento de login fallido — email no registrado");
                    return new BadCredentialsException("Credenciales inválidas");
                });

        // Verificar la contraseña contra el hash almacenado en BD
        if (!passwordEncoder.matches(password, adminUser.getPassword())) {
            log.warn("Intento de login fallido — password incorrecto");
            throw new BadCredentialsException("Credenciales inválidas");
        }

        // Verificar que la cuenta esté activa
        if (!adminUser.isEnabled()) {
             log.warn("Intento de login fallido — cuenta inactiva");
            throw new BadCredentialsException("Credenciales inválidas");
        }

        // Credenciales válidas → generar token JWT (sin tenant_id)
        String token = jwtTokenProvider.generateAdminToken(adminUser);

        log.info("Login de administrador exitoso");

        return new AuthResponse(token);
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ───��────────────────────────────────────────────
// 1. Mismo mensaje de error para email y password incorrectos:
//    Previene enumeration attacks — el atacante no sabe qué cuentas existen.
// 2. Tres verificaciones secuenciales:
//    a) Email existe → si no, error inmediato
//    b) Password correcta → si no, error
//    c) Cuenta activa → si no, error
//    El orden importa: verificar password ANTES de isEnabled() permite
//    intentar credenciales de cuentas inactivas (para debugging).
// 3. generateAdminToken() vs generateToken(): no incluye tenant_id
//    porque admins operan en schema "public" (global).
// 4. @Transactional(readOnly = true): el login no modifica BD,
//    solo consulta → mejor rendimiento.
// ─────────────────────────────────────────────────────────────────────────────