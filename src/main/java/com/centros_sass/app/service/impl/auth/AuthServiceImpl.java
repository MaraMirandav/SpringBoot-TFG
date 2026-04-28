package com.centros_sass.app.service.impl.auth;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.auth.AuthResponse;
import com.centros_sass.app.dto.auth.LoginRequest;
import com.centros_sass.app.dto.auth.RegisterRequest;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.catalogs.organization.RoleRepository;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.core.security.JwtTokenProvider;
import com.centros_sass.core.security.LoginRateLimiter;
import com.centros_sass.core.security.WorkerSecurity;
import com.centros_sass.core.tenant.TenantContext;
import com.centros_sass.app.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final WorkerRepository workerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginRateLimiter loginRateLimiter;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Establecer tenant context ANTES de cualquier operación en BD
        // Sin esto Hibernate busca en schema "public" y workers no existe ahí
        if (request.tenantId() != null && !request.tenantId().isBlank()) {
            TenantContext.set(request.tenantId());
        }

        try {
            String normalizedEmail = request.email().trim().toLowerCase();

            if (workerRepository.existsByEmail(normalizedEmail)) {
                throw new IllegalArgumentException("El email ya está registrado");
            }

            var worker = new Worker();
            worker.setEmail(normalizedEmail);
            worker.setPassword(passwordEncoder.encode(request.password()));
            worker.setFirstName(request.firstName());
            worker.setSecondName(request.secondName());
            worker.setFirstSurname(request.firstSurname());
            worker.setSecondSurname(request.secondSurname());
            worker.setDni(request.dni());
            worker.setMainPhone(request.mainPhone());
            worker.setSecondPhone(request.secondPhone());
            worker.setIsActive(true);
            worker.setTenantId(request.tenantId()); // ← guardar tenantId en el worker

            workerRepository.save(worker);

            var defaultRole = roleRepository.findByRoleName("ROLE_TAS")
                    .orElseThrow(() -> new IllegalStateException(
                        "El rol ROLE_TAS no existe en la base de datos"));
            worker.addRole(defaultRole);
            workerRepository.save(worker);

            var workerSecurity = new WorkerSecurity(worker);
            String token = jwtTokenProvider.generateToken(workerSecurity, null);
            return new AuthResponse(token);

        } finally {
            TenantContext.clear(); // SIEMPRE limpiar — aunque haya error
        }
    }

    @Override
    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        // Obtener IP del cliente, respetando proxies (X-Forwarded-For)
        String ip = Optional.ofNullable(httpRequest.getHeader("X-Forwarded-For"))
                .map(h -> h.split(",")[0].trim())
                .orElse(httpRequest.getRemoteAddr());

        // Verificar rate limit ANTES de cualquier query a BD
        // Esto bloquea ataques de fuerza bruta sin coste de base de datos
        loginRateLimiter.checkLimit(ip);

        // Establecer tenant context ANTES de cualquier query a BD.
        // DaoAuthenticationProvider (usado por AuthenticationManager) abría su propia
        // sesión Hibernate antes de que pudiésemos llamar TenantContext.set(), lo que
        // hacía que la query fuera al schema "public" en vez del schema del tenant.
        // Solución: verificación manual de credenciales — controlamos el orden de operaciones.
        String slug = request.tenantId();
        if (request.tenantId() != null && !request.tenantId().isBlank()) {
            TenantContext.set(request.tenantId());
        }

        try {
            String normalizedEmail = request.email().trim().toLowerCase();

            // 1. Buscar worker directamente (TenantContext ya está seteado).
            //    findByEmailWithRoles hace JOIN FETCH de roles para evitar lazy loading
            //    después de que el contexto se limpie en el finally.
            var worker = workerRepository.findByEmailWithRoles(normalizedEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("Credenciales inválidas"));

            // 2. Verificar que el worker está activo.
            //    Mismo mensaje genérico — anti-enumeration: el atacante no sabe si el email existe.
            if (!worker.getIsActive()) {
                log.warn("LOGIN_FAILED tenant={} ip={}", slug, ip);
                throw new UsernameNotFoundException("Credenciales inválidas");
            }

            // 3. Verificar password manualmente con BCrypt.
            //    passwordEncoder.matches() compara el texto plano con el hash almacenado en BD.
            if (!passwordEncoder.matches(request.password(), worker.getPassword())) {
                log.warn("LOGIN_FAILED tenant={} ip={}", slug, ip);
                throw new UsernameNotFoundException("Credenciales inválidas");
            }

            // 4. Credenciales correctas → generar JWT con los datos del worker.
            var workerSecurity = new WorkerSecurity(worker);
            String token = jwtTokenProvider.generateToken(workerSecurity, null);

            log.info("LOGIN_SUCCESS tenant={} ip={}", slug, ip);
            return new AuthResponse(token);

        } finally {
            TenantContext.clear(); // SIEMPRE limpiar — aunque haya error
        }
    }
}
