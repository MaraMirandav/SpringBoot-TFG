package com.centros_sass.admin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

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
