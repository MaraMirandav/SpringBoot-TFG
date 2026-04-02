package com.centros_sass.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.centros_sass.app.model.base.AuditingContextHolder;

@Configuration(proxyBeanMethods = false)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfig {

    public JpaAuditConfig(AuditorAware<String> auditorProvider) {
        AuditingContextHolder.setAuditorAware(auditorProvider);
    }
}
