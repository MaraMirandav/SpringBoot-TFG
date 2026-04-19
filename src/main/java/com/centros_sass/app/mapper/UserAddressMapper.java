package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.useraddress.UserAddressRequestDTO;
import com.centros_sass.app.dto.useraddress.UserAddressResponseDTO;
import com.centros_sass.app.dto.useraddress.UserAddressUpdateDTO;
import com.centros_sass.app.model.profiles.users.UserAddress;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserAddressMapper {

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", expression = "java(formatCityName(entity))")
    @Mapping(target = "provinceId", source = "province.id")
    @Mapping(target = "provinceName", expression = "java(formatProvinceName(entity))")
    @Mapping(target = "regionId", source = "region.id")
    @Mapping(target = "regionName", expression = "java(formatRegionName(entity))")
    @Mapping(target = "createdAt", expression = "java(formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(formatDateTime(entity.getUpdatedAt()))")
    UserAddressResponseDTO toResponse(UserAddress entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "province", ignore = true)
    @Mapping(target = "region", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    UserAddress toEntity(UserAddressRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "province", ignore = true)
    @Mapping(target = "region", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateFromDto(UserAddressUpdateDTO dto, @MappingTarget UserAddress entity);

    default String formatCityName(UserAddress entity) {
        return entity.getCity() != null ? entity.getCity().getCityName() : null;
    }

    default String formatProvinceName(UserAddress entity) {
        return entity.getProvince() != null ? entity.getProvince().getProvinceName() : null;
    }

    default String formatRegionName(UserAddress entity) {
        return entity.getRegion() != null ? entity.getRegion().getRegionName() : null;
    }

    default String formatDateTime(java.time.LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toString();
    }
}