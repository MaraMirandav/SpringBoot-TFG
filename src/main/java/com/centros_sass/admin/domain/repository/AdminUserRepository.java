package com.centros_sass.admin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.centros_sass.admin.domain.model.*;

/**
 * AdminUserRepository — acceso a la tabla admin_users en el schema public.
 *
 * Output port para los use cases que gestionan empleados del panel admin.
 * Separado de TenantRepository aunque ambos estén en el mismo módulo admin:
 * cada entidad tiene su propio repositorio — principio de responsabilidad única.
 */
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Long> {

    /**
     * Busca un admin por email — operación de login del panel.
     * Spring Security llamará esto para cargar el usuario y verificar contraseña.
     *
     * Optional para que el use case de login maneje "email no registrado"
     * lanzando credentialsException en vez de NullPointerException.
     *
     * @param email email corporativo del admin (ej: "juan@saascon.com")
     * @return el admin si existe, Optional.empty() si no hay cuenta con ese email
     */
    Optional<AdminUserEntity> findByEmail(String email);

    /**
     * Lista admins por rol — útil para el panel de gestión de usuarios internos.
     *
     * @param role SUPER_ADMIN, ADMIN o SUPPORT
     * @return admins con ese rol
     */
    List<AdminUserEntity> findByRole(AdminUserRole role);

    /**
     * Lista admins por estado — para ver activos vs desactivados.
     *
     * @param status ACTIVE o INACTIVE
     * @return admins con ese estado
     */
    List<AdminUserEntity> findByStatus(AdminUserStatus status);

    /**
     * Verifica si ya existe una cuenta con ese email — para validar unicidad antes de crear.
     * Más eficiente que findByEmail().isPresent() al no cargar la entidad completa.
     *
     * @param email el email a verificar
     * @return true si ya hay un admin registrado con ese email
     */
    boolean existsByEmail(String email);
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. Un repositorio por entidad: TenantRepository para TenantEntity,
//    AdminUserRepository para AdminUserEntity — cada uno con sus queries específicos
// 2. findByEmail devuelve Optional: el login use case maneja "no encontrado" con gracia
//    entity.orElseThrow(() -> new UsernameNotFoundException(...)) es el patrón Spring Security
// 3. existsByEmail: verificación antes de insertar — evita DataIntegrityViolationException
//    Mejor UX: devolver "email ya registrado" (400) que un 500 por constraint violation
// 4. Todos los queries son derivados del nombre — cero SQL para operaciones simples
//    Solo se necesita @Query para joins complejos o condiciones múltiples (OR, LIKE, etc.)
// ─────────────────────────────────────────────────────────────────────────────
