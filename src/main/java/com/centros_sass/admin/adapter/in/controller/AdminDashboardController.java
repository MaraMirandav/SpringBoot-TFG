package com.centros_sass.admin.adapter.in.controller;

import com.centros_sass.admin.adapter.in.dto.AdminDashboardStatsResponse;
import com.centros_sass.admin.application.service.TenantQueryService;
import com.centros_sass.app.generic.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final TenantQueryService tenantQueryService;

    @GetMapping("/stats")
    public ResponseEntity<ApiDataResponse<AdminDashboardStatsResponse>> getStats() {
        return ResponseEntity.ok(
            new ApiDataResponse<>("Estadísticas del dashboard",
                tenantQueryService.getDashboardStats(), 200));
    }
}