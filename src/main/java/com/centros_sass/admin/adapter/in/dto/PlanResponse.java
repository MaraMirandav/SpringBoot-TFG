package com.centros_sass.admin.adapter.in.dto;

import java.math.BigDecimal;
import java.util.Map;

public record PlanResponse(
    Long id,
    String name,
    String slug,
    BigDecimal priceMonthly,
    Integer maxWorkers,
    Integer maxUsers,
    Map<String, Boolean> features,
    String status
) {}