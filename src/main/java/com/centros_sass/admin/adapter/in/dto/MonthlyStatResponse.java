package com.centros_sass.admin.adapter.in.dto;

public record MonthlyStatResponse(
    String month,
    long count
) {}