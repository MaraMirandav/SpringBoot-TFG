package com.centros_sass.app.model.base;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditingContextHolder {

    private static AuditorAware<String> auditorAware;

    public static void setAuditorAware(AuditorAware<String> auditorAware) {
        AuditingContextHolder.auditorAware = auditorAware;
    }

    public static Optional<String> getCurrentAuditor() {
        if (auditorAware == null) {
            return Optional.empty();
        }
        return auditorAware.getCurrentAuditor();
    }
}
