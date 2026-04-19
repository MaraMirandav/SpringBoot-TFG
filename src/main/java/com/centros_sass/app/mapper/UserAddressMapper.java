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

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.cityName")
    @Mapping(target = "provinceId", source = "province.id")
    @Mapping(target = "provinceName", source = "province.provinceName")
    @Mapping(target = "regionId", source = "region.id")
    @Mapping(target = "regionName", source = "region.regionName")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
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

    default String formatDateTime(java.time.LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toString();
    }
}