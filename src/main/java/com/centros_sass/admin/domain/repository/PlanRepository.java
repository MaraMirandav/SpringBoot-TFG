package com.centros_sass.admin.domain.repository;

import com.centros_sass.admin.domain.model.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlanRepository extends JpaRepository<PlanEntity, Long> {
    List<PlanEntity> findByStatus(String status);
    boolean existsBySlug(String slug);
}