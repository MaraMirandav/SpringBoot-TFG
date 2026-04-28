package com.centros_sass.admin.application.service;

import com.centros_sass.admin.adapter.in.dto.CreatePlanRequest;
import com.centros_sass.admin.adapter.in.dto.PlanResponse;
import com.centros_sass.admin.adapter.in.dto.PublicPlanResponse;
import com.centros_sass.admin.adapter.in.dto.UpdatePlanRequest;
import com.centros_sass.admin.domain.model.PlanEntity;
import com.centros_sass.admin.domain.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public Page<PlanResponse> findAll(Pageable pageable) {
        return planRepository.findAll(pageable).map(this::mapToResponse);
    }

    public PlanResponse findById(Long id) {
        return planRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
    }

    @Transactional
    public PlanResponse create(CreatePlanRequest request) {
        if (planRepository.existsBySlug(request.slug())) {
            throw new RuntimeException("Ya existe un plan con ese slug");
        }
        PlanEntity entity = PlanEntity.builder()
                .name(request.name())
                .slug(request.slug())
                .priceMonthly(request.priceMonthly())
                .maxWorkers(request.maxWorkers())
                .maxUsers(request.maxUsers())
                .features(request.features())
                .status(request.status() != null ? request.status() : "ACTIVE")
                .build();
        return mapToResponse(planRepository.save(entity));
    }

    @Transactional
    public PlanResponse update(Long id, UpdatePlanRequest request) {
        PlanEntity existing = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        existing.setName(request.name());
        existing.setPriceMonthly(request.priceMonthly());
        existing.setMaxWorkers(request.maxWorkers());
        existing.setMaxUsers(request.maxUsers());
        existing.setFeatures(request.features());
        if (request.status() != null) existing.setStatus(request.status());
        return mapToResponse(planRepository.save(existing));
    }

    @Transactional
    public PlanResponse updateStatus(Long id, String status) {
        PlanEntity plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        plan.setStatus(status);
        return mapToResponse(planRepository.save(plan));
    }

    @Transactional
    public void delete(Long id) {
        PlanEntity plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        plan.setStatus("DEPRECATED");
        planRepository.save(plan);
    }

    public List<PublicPlanResponse> findAllActive() {
        return planRepository.findByStatus("ACTIVE")
                .stream()
                .map(p -> new PublicPlanResponse(
                        p.getId(),
                        p.getName(),
                        p.getSlug(),
                        p.getPriceMonthly(),
                        p.getMaxWorkers(),
                        p.getMaxUsers(),
                        p.getFeatures(),
                        "profesional".equals(p.getSlug())
                ))
                .toList();
    }

    private PlanResponse mapToResponse(PlanEntity p) {
        return new PlanResponse(
                p.getId(), p.getName(), p.getSlug(),
                p.getPriceMonthly(), p.getMaxWorkers(),
                p.getMaxUsers(), p.getFeatures(), p.getStatus());
    }
}