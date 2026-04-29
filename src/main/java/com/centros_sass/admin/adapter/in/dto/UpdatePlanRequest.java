package com.centros_sass.admin.adapter.in.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Map;

public record UpdatePlanRequest(
    @NotBlank String name,
    @NotBlank String slug,
    @NotNull @PositiveOrZero BigDecimal priceMonthly,
    Integer maxWorkers,
    @NotNull Integer maxUsers,
    Map<String, Boolean> features,
    String status
) {}