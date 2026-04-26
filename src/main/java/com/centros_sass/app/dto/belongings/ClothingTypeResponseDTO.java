package com.centros_sass.app.dto.belongings;

/**
 * DTO de respuesta para retornar datos de un tipo de prenda.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record ClothingTypeResponseDTO(
    Integer id,
    String clothingName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
