package com.centros_sass.customer.adapter.in.dto;

public record CustomerRegisterResponse(
    String tenantId,
    String tenantName,
    String directorEmail,
    String token
) {}