package com.centros_sass.app.model.base;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class AuditingListener {

    @PrePersist
    public void touchForCreate(BaseEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        String auditor = AuditingContextHolder.getCurrentAuditor().orElse("system");

        entity.setCreatedAt(now);
        entity.setCreatedBy(auditor);
        entity.setUpdatedAt(now);
        entity.setUpdatedBy(auditor);
    }

    @PreUpdate
    public void touchForUpdate(BaseEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(AuditingContextHolder.getCurrentAuditor().orElse("system"));
    }
}
