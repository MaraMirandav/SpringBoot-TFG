package com.centros_sass.admin.adapter.in.mapper;

import com.centros_sass.admin.adapter.in.dto.TenantResponse;
import com.centros_sass.admin.domain.model.TenantEntity;
import org.mapstruct.Mapper;

/**
 * TenantMapper — mapea entre TenantEntity y TenantResponse.
 *
 * MapStruct genera la implementación en tiempo de compilación.
 * componentModel = "spring" permite inyectar el mapper como Spring Bean.
 */
@Mapper(componentModel = "spring")
public interface TenantMapper {

    /**
     * Convierte TenantEntity a TenantResponse.
     *
     * Solo mapea campos necesarios para la API.
     * Nunca expone datos sensibles como passwordHash.
     *
     * @param entity el TenantEntity desde la BD
     * @return TenantResponse para la API
     */
    TenantResponse toResponse(TenantEntity entity);
}