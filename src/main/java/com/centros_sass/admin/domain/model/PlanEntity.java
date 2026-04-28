package com.centros_sass.admin.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;


@Entity
@Table(name = "plans", schema = "public")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String slug;

    @Column(name = "price_monthly", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceMonthly;

    @Column(name = "max_users", nullable = false)
    private Integer maxUsers;

    @Column(name = "max_workers", nullable = false)
    private Integer maxWorkers;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> features;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}