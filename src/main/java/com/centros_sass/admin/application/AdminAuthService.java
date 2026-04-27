package com.centros_sass.admin.application;

import com.centros_sass.app.dto.auth.AuthResponse;
import com.centros_sass.app.dto.auth.LoginRequest;

/**
 * AdminAuthService — puerto para la autenticación de administradores internos en el panel admin.
 *
 * Este servicio maneja el login de empleados de SaasCon que acceden al panel
 * de administración. A diferencia de AuthService (app), este opera sobre la tabla
 * admin_users en el schema "public" y no requiere tenant context.
 *
 * El flujo es:
 *   1. AdminController recibe LoginRequest (email + password)
 *   2. AdminAuthService.login() verifica credenciales contra admin_users
 *   3. Si válidas, genera JWT sin tenant_id (solo para admins internos)
 *   4. Devuelve AuthResponse con el token
 */
public interface AdminAuthService {

    /**
     * Autentica un administrador interno con email y contraseña.
     *
     * Busca el admin por email en la tabla admin_users, verifica la contraseña
     * hasheada con BCrypt, y si todo es correcto genera un JWT token.
     *
     * Por seguridad, retorna el MISMO mensaje de error ("Credenciales inválidas")
     * tanto si el email no existe como si la contraseña es incorrecta.
     * Esto evita revelar qué cuentas existen en el sistema.
     *
     * @param request LoginRequest con email y password
     * @return AuthResponse con el token JWT generado
     */
    AuthResponse login(LoginRequest request);
}