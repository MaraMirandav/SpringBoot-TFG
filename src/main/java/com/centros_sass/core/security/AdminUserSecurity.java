package com.centros_sass.core.security;

import com.centros_sass.admin.domain.model.AdminUserEntity;
import com.centros_sass.admin.domain.model.AdminUserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * AdminUserSecurity — adaptador que envuelve AdminUserEntity para Spring Security.
 *
 * Spring Security no sabe qué es un AdminUserEntity. Solo entiende objetos
 * que implementen la interfaz UserDetails. Esta clase es el "traductor" entre
 * el mundo de negocio (AdminUserEntity) y el mundo de seguridad (UserDetails).
 *
 * ¿Por qué existe si ya tenemos WorkerSecurity?
 * Son dos tipos de usuario completamente distintos:
 *
 *   WorkerSecurity   → empleado de un centro de día (pertenece a UN tenant)
 *                      su JWT incluye tenant_id → Hibernate apunta a schema tenant_*
 *
 *   AdminUserSecurity → empleado interno de SaasCon (NO pertenece a ningún tenant)
 *                       su JWT NO incluye tenant_id → accede al schema "public" global
 *                       puede ver y gestionar TODOS los tenants desde el panel admin
 *
 * Separar las dos clases evita mezclar lógica de negocio con lógica de administración.
 */
public class AdminUserSecurity implements UserDetails {

    // El entity real con los datos del admin — final porque nunca cambia tras construir
    private final AdminUserEntity adminUser;

    /**
     * Constructor — recibe el AdminUserEntity cargado desde la base de datos.
     *
     * Spring Security llama a UserDetailsService.loadUserByUsername() para obtener
     * este objeto. El UserDetailsService lo crea pasando el entity encontrado por email.
     *
     * @param adminUser el entity del admin autenticado, cargado desde la tabla admin_users
     */
    public AdminUserSecurity(AdminUserEntity adminUser) {
        this.adminUser = adminUser;
    }

    /**
     * Devuelve los permisos (authorities) del admin para Spring Security.
     *
     * A diferencia de WorkerSecurity, donde un worker puede tener MÚLTIPLES roles
     * (ej: TAS + COORDINADOR), un AdminUser tiene exactamente UN rol (enum AdminUserRole).
     * Esto refleja la jerarquía interna de SaasCon: SUPER_ADMIN, ADMIN o SUPPORT.
     *
     * adminUser.getRole().name() convierte el enum a String:
     *   AdminUserRole.SUPER_ADMIN → "SUPER_ADMIN"
     *   AdminUserRole.ADMIN       → "ADMIN"
     *   AdminUserRole.SUPPORT     → "SUPPORT"
     *
     * Spring Security usa este valor en SecurityConfig con hasRole("SUPER_ADMIN"), etc.
     *
     * @return lista con exactamente una authority que representa el rol del admin
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // List.of() crea una lista inmutable con un solo elemento — correcto para un rol único
        return List.of(new SimpleGrantedAuthority(adminUser.getRole().name()));
    }

    /**
     * Devuelve la contraseña hasheada del admin para que Spring Security la compare.
     *
     * IMPORTANTE: retorna el HASH BCrypt almacenado en BD, NO la contraseña en texto plano.
     * Spring Security llama a PasswordEncoder.matches(rawPassword, encodedPassword)
     * donde encodedPassword es lo que retorna este método.
     * Nunca se almacena ni se devuelve la contraseña en texto plano.
     *
     * @return hash BCrypt de la contraseña (ej: "$2a$12$...")
     */
    @Override
    public String getPassword() {
        return adminUser.getPasswordHash();
    }

    /**
     * Devuelve el identificador único del admin — en este caso su email.
     *
     * Spring Security usa este valor como "username" para identificar al usuario.
     * Cuando se genera el JWT, este valor se convierte en el claim "sub" (subject).
     * El email es único en la tabla admin_users (constraint UNIQUE en BD).
     *
     * @return email del admin (ej: "juan@saascon.com")
     */
    @Override
    public String getUsername() {
        return adminUser.getEmail();
    }

    /**
     * Indica si la cuenta del admin ha expirado.
     * Retornamos true (no expira) porque AdminUserEntity no tiene
     * campo de expiración de cuenta — la desactivación se maneja
     * con AdminUserStatus.INACTIVE, no con expiración.
     *
     * @return true siempre — la expiración no aplica para admins internos
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del admin está bloqueada.
     * Retornamos true (no bloqueada) porque el bloqueo temporal no está
     * implementado en este momento. En producción podría añadirse un campo
     * "lockedUntil" en AdminUserEntity para bloquear tras N intentos fallidos.
     *
     * @return true siempre — el bloqueo temporal no está implementado todavía
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales (contraseña) han expirado.
     * Retornamos true (no expiradas) porque la rotación obligatoria de contraseñas
     * no está implementada. En un entorno enterprise podría forzarse cada 90 días.
     *
     * @return true siempre — la expiración de credenciales no está implementada
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del admin está activa y puede autenticarse.
     *
     * Este es el check más importante de los cuatro métodos booleanos.
     * Cuando un admin se desactiva (deja la empresa), su status cambia a INACTIVE.
     * Spring Security llama a isEnabled() y si retorna false, lanza
     * DisabledException antes de verificar la contraseña — el admin no puede entrar.
     *
     * ¿Por qué no borramos el registro en vez de desactivarlo?
     * Porque audit_log guarda referencias al user_id del admin.
     * Si borramos el registro, perdemos la trazabilidad de quién hizo qué.
     * El "soft delete" con status INACTIVE preserva el historial de auditoría.
     *
     * @return true si el admin está ACTIVE, false si está INACTIVE
     */
    @Override
    public boolean isEnabled() {
        // Comparar el status del entity con el enum ACTIVE
        return adminUser.getStatus() == AdminUserStatus.ACTIVE;
    }

    /**
     * Devuelve el entity original para acceder a datos específicos del admin.
     *
     * UserDetails solo expone getUsername() y getPassword().
     * Para acceder a campos como getId(), getRole(), getFullName(),
     * necesitamos este getter que expone el entity completo.
     *
     * Se usa principalmente en JwtTokenProvider.generateAdminToken()
     * para extraer id, role y fullName al construir el JWT.
     *
     * @return el AdminUserEntity original con todos sus datos
     */
    public AdminUserEntity getAdminUser() {
        return adminUser;
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. Patrón Adapter: AdminUserSecurity adapta AdminUserEntity (mundo de negocio)
//    a UserDetails (mundo de Spring Security) sin modificar ninguno de los dos.
//    Es un ejemplo clásico del patrón de diseño Adapter.
//
// 2. UserDetails vs Entity: nunca exponer el entity directamente a Spring Security.
//    El adapter controla exactamente qué datos son visibles para el framework.
//
// 3. isEnabled() como soft delete: en vez de borrar registros, desactivarlos.
//    Preserva la integridad referencial en audit_log y el historial completo.
//
// 4. Un rol vs múltiples roles: los admins internos tienen jerarquía clara
//    (SUPER_ADMIN > ADMIN > SUPPORT) → un solo rol es suficiente y más simple.
//    Los workers del centro pueden tener múltiples roles simultáneos → colección.
//
// 5. AdminUserSecurity vs WorkerSecurity: misma interfaz, mundos distintos.
//    WorkerSecurity → tenant-scoped, JWT con tenant_id
//    AdminUserSecurity → global, JWT sin tenant_id
// ─────────────────────────────────────────────────────────────────────────────