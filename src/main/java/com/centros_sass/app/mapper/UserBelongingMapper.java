package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.belongings.UserBelongingRequestDTO;
import com.centros_sass.app.dto.belongings.UserBelongingResponseDTO;
import com.centros_sass.app.dto.belongings.UserBelongingUpdateDTO;
import com.centros_sass.app.model.belongings.UserBelonging;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserBelongingMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", expression = "java(entity.getUser() != null ? entity.getUser().getFirstName() + \" \" + entity.getUser().getFirstSurname() : null)")
    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerFullName", expression = "java(entity.getWorker() != null ? entity.getWorker().getFirstName() + \" \" + entity.getWorker().getFirstSurname() : null)")
    @Mapping(target = "userClothingId", source = "userClothing.id")
    @Mapping(target = "clothingTypeName", expression = "java(entity.getUserClothing() != null && entity.getUserClothing().getClothingType() != null ? entity.getUserClothing().getClothingType().getClothingName() : null)")
    @Mapping(target = "userDiaperId", source = "userDiaper.id")
    @Mapping(target = "diaperSizeName", expression = "java(entity.getUserDiaper() != null && entity.getUserDiaper().getDiaperSize() != null ? entity.getUserDiaper().getDiaperSize().getSize() : null)")
    @Mapping(target = "diaperTypeName", expression = "java(entity.getUserDiaper() != null && entity.getUserDiaper().getDiaperType() != null ? entity.getUserDiaper().getDiaperType().getType() : null)")
    @Mapping(target = "userObjectId", source = "userObject.id")
    @Mapping(target = "objectTypeName", expression = "java(entity.getUserObject() != null && entity.getUserObject().getObjectType() != null ? entity.getUserObject().getObjectType().getObjectName() : null)")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    UserBelongingResponseDTO toResponse(UserBelonging entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "userClothing", ignore = true)
    @Mapping(target = "userDiaper", ignore = true)
    @Mapping(target = "userObject", ignore = true)
    UserBelonging toEntity(UserBelongingRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "userClothing", ignore = true)
    @Mapping(target = "userDiaper", ignore = true)
    @Mapping(target = "userObject", ignore = true)
    void updateFromDto(UserBelongingUpdateDTO dto, @MappingTarget UserBelonging entity);

}
