package com.centros_sass.admin.application.service;

import com.centros_sass.admin.adapter.in.dto.AdminProfileResponse;
import com.centros_sass.admin.adapter.in.dto.AdminUserResponse;
import com.centros_sass.admin.adapter.in.dto.CreateAdminUserRequest;
import com.centros_sass.admin.adapter.in.dto.UpdateAdminProfileRequest;
import com.centros_sass.admin.domain.model.AdminUserEntity;
import com.centros_sass.admin.domain.model.AdminUserRole;
import com.centros_sass.admin.domain.model.AdminUserStatus;
import com.centros_sass.admin.domain.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<AdminUserResponse> findAll(Pageable pageable) {
        return adminUserRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public AdminUserResponse findById(Long id) {
        return adminUserRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Usuario admin no encontrado"));
    }

    @Transactional
    public AdminUserResponse create(CreateAdminUserRequest request) {
        if (adminUserRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Ya existe un usuario con este email");
        }

        AdminUserEntity entity = AdminUserEntity.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .role(request.role())
                .status(AdminUserStatus.ACTIVE)
                .build();

        return mapToResponse(adminUserRepository.save(entity));
    }

    @Transactional
    public AdminUserResponse updateStatus(Long id, AdminUserStatus status) {
        AdminUserEntity entity = adminUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario admin no encontrado"));

        entity.setStatus(status);
        return mapToResponse(adminUserRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        AdminUserEntity entity = adminUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario admin no encontrado"));

        entity.setStatus(AdminUserStatus.INACTIVE);
        adminUserRepository.save(entity);
    }

    /**
     * Obtiene el perfil del admin logueado basado en su email.
     *
     * Este método se usa en el endpoint GET /api/v1/admin/profile.
     * El email se extrae del JWT (claim "sub") en el controller.
     *
     * @param email email del admin (del token JWT)
     * @return AdminProfileResponse con los datos del perfil
     * @throws RuntimeException si no se encuentra el admin
     */
    public AdminProfileResponse getProfile(String email) {
        AdminUserEntity entity = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario admin no encontrado"));

        return mapToProfileResponse(entity);
    }

/**
     * Actualiza el perfil del admin: fullName y/o contraseña.
     *
     * Lógica de contraseñas:
     *   - Si currentPassword y newPassword son null → solo actualizar fullName
     *   - Si ambos tienen valor → verificar currentPassword y actualizar contraseña
     *   - Si solo uno tiene valor → throw RuntimeException (error 400)
     *
     * @param email   email del admin (del token JWT)
     * @param request request con fullName (opcional), currentPassword (opcional), newPassword (opcional)
     * @return AdminProfileResponse con los datos actualizados
     * @throws RuntimeException si la contraseña actual es incorrecta o falta una de las dos
     */
    @Transactional
    public AdminProfileResponse updateProfile(String email, UpdateAdminProfileRequest request) {
        // 1. Buscar el admin por email
        AdminUserEntity entity = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario admin no encontrado"));

        // 2. Determinar si se quiere cambiar la contraseña
        boolean wantsToChangePassword = request.currentPassword() != null || request.newPassword() != null;

        if (wantsToChangePassword) {
            // 2a. Ambos campos deben estar presentes para cambiar contraseña
            if (request.currentPassword() == null || request.newPassword() == null) {
                throw new RuntimeException("Debes enviar ambas contraseñas para cambiarla");
            }

            // 2b. Validar la contraseña actual
            if (!passwordEncoder.matches(request.currentPassword(), entity.getPasswordHash())) {
                throw new RuntimeException("La contraseña actual es incorrecta");
            }

            // 2c. Actualizar contraseña
            entity.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        }

        // 3. Actualizar fullName si viene y no está vacío
        if (request.fullName() != null && !request.fullName().isBlank()) {
            entity.setFullName(request.fullName());
        }

// 4. Guardar cambios y retornar respuesta
        AdminUserEntity saved = adminUserRepository.save(entity);
        return mapToProfileResponse(saved);
    }

    /**
     * Mapea AdminUserEntity a AdminProfileResponse.
     *
     * @param entity entidad del admin
     * @return DTO de respuesta del perfil
     */
    private AdminProfileResponse mapToProfileResponse(AdminUserEntity entity) {
        return new AdminProfileResponse(
                entity.getId(),
                entity.getEmail(),
                entity.getFullName(),
                entity.getRole().name(),
                entity.getCreatedAt()
        );
    }

    private AdminUserResponse mapToResponse(AdminUserEntity entity) {
        return new AdminUserResponse(
                entity.getId(),
                entity.getEmail(),
                entity.getFullName(),
                entity.getRole().name(),
                entity.getStatus().name(),
                entity.getCreatedAt()
        );
    }
}