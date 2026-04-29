package com.centros_sass.customer.application.service;

import com.centros_sass.admin.application.service.TenantProvisioningService;
import com.centros_sass.core.security.JwtTokenProvider;
import com.centros_sass.core.security.LoginRateLimiter;
import com.centros_sass.customer.adapter.in.dto.CustomerRegisterRequest;
import com.centros_sass.customer.adapter.in.dto.CustomerRegisterResponse;
import com.centros_sass.core.security.WorkerSecurity;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.core.tenant.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerRegistrationService {

    private final TenantProvisioningService tenantProvisioningService;
    private final WorkerRepository workerRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginRateLimiter loginRateLimiter;

    public CustomerRegisterResponse register(
            CustomerRegisterRequest request,
            HttpServletRequest httpRequest) {

        String ip = Optional.ofNullable(
                httpRequest.getHeader("X-Forwarded-For"))
                .map(h -> h.split(",")[0].trim())
                .orElse(httpRequest.getRemoteAddr());

        loginRateLimiter.checkLimit(ip);

        tenantProvisioningService.provisionTenant(
                request.centerName(),
                request.slug(),
                request.directorEmail(),
                request.planSlug(),
                request.directorEmail(),
                request.directorPassword(),
                request.directorFirstName(),
                request.directorFirstSurname(),
                request.directorDni(),
                request.directorPhone()
        );

        TenantContext.set(request.slug());
        try {
            Worker worker = workerRepository.findByEmailWithRoles(request.directorEmail().trim().toLowerCase())
                    .orElseThrow(() -> new RuntimeException("Director no encontrado"));

            String token = jwtTokenProvider.generateToken(
                    new WorkerSecurity(worker),
                    request.planSlug() != null ? request.planSlug() : "basico"
            );

            log.info("CUSTOMER_REGISTER_SUCCESS tenant={} ip={}",
                    request.slug(), ip);

            return new CustomerRegisterResponse(
                    request.slug(),
                    request.centerName(),
                    request.directorEmail(),
                    token
            );
        } finally {
            TenantContext.clear();
        }
    }
}