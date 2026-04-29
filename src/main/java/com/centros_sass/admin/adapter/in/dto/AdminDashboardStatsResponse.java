package com.centros_sass.admin.adapter.in.dto;

import java.util.List;

public record AdminDashboardStatsResponse(
    long totalTenants,
    long activeTenants,
    long suspendedTenants,
    long cancelledTenants,
    long totalWorkers,
    List<TenantResponse> recentTenants,
    List<MonthlyStatResponse> tenantsByMonth
) {}