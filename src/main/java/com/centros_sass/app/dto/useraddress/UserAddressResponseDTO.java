package com.centros_sass.app.dto.useraddress;

public record UserAddressResponseDTO(
    Integer id,
    Integer userId,
    String address,
    String postalCode,
    Integer cityId,
    String cityName,
    Integer provinceId,
    String provinceName,
    Integer regionId,
    String regionName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}