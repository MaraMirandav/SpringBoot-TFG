package com.centros_sass.app.service.impl.belongings;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.belongings.UserObjectRequestDTO;
import com.centros_sass.app.dto.belongings.UserObjectResponseDTO;
import com.centros_sass.app.dto.belongings.UserObjectUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.belongings.UserObjectMapper;
import com.centros_sass.app.model.belongings.UserObject;
import com.centros_sass.app.model.catalogs.belongings.ItemCondition;
import com.centros_sass.app.model.catalogs.belongings.ObjectType;
import com.centros_sass.app.repository.catalogs.belongings.ItemConditionRepository;
import com.centros_sass.app.repository.catalogs.belongings.ObjectTypeRepository;
import com.centros_sass.app.repository.belongings.UserObjectRepository;
import com.centros_sass.app.service.UserObjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserObjectServiceImpl implements UserObjectService {

    private final UserObjectRepository userObjectRepository;
    private final ObjectTypeRepository objectTypeRepository;
    private final ItemConditionRepository itemConditionRepository;
    private final UserObjectMapper userObjectMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserObjectResponseDTO> findAll(Pageable pageable) {
        return userObjectRepository.findAllByIsActiveTrue(pageable)
                .map(userObjectMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserObjectResponseDTO> findAllInactive(Pageable pageable) {
        return userObjectRepository.findAllByIsActiveFalse(pageable)
                .map(userObjectMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserObjectResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return userObjectRepository.findAll(pageable)
                .map(userObjectMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserObjectResponseDTO> findById(Integer id) {
        return userObjectRepository.findById(id)
                .map(userObjectMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserObjectResponseDTO> findByObjectTypeId(Integer objectTypeId, Pageable pageable) {
        return userObjectRepository.findAllByObjectTypeId(objectTypeId, pageable)
                .map(userObjectMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserObjectResponseDTO> findByItemConditionId(Integer itemConditionId, Pageable pageable) {
        return userObjectRepository.findAllByItemConditionId(itemConditionId, pageable)
                .map(userObjectMapper::toResponse);
    }

    @Override
    @Transactional
    public UserObjectResponseDTO save(UserObjectRequestDTO dto) {
        UserObject obj = userObjectMapper.toEntity(dto);
        assignRelations(obj, dto.objectTypeId(), dto.itemConditionId());
        UserObject saved = userObjectRepository.save(obj);
        return userObjectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserObjectResponseDTO> update(Integer id, UserObjectUpdateDTO dto) {
        return userObjectRepository.findById(id).map(existing -> {
            if (dto.getObjectTypeId() != null) {
                ObjectType type = objectTypeRepository.findById(dto.getObjectTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("ObjectType", "id", dto.getObjectTypeId()));
                existing.setObjectType(type);
            }
            if (dto.getItemConditionId() != null) {
                ItemCondition condition = itemConditionRepository.findById(dto.getItemConditionId())
                        .orElseThrow(() -> new ResourceNotFoundException("ItemCondition", "id", dto.getItemConditionId()));
                existing.setItemCondition(condition);
            }

            userObjectMapper.updateFromDto(dto, existing);
            UserObject saved = userObjectRepository.saveAndFlush(existing);
            return userObjectMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<UserObjectResponseDTO> delete(Integer id) {
        return userObjectRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            UserObject saved = userObjectRepository.saveAndFlush(existing);
            return userObjectMapper.toResponse(saved);
        });
    }

    // Métodos Helpers

    private void assignRelations(UserObject obj, Integer objectTypeId, Integer itemConditionId) {
        ObjectType type = objectTypeRepository.findById(objectTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("ObjectType", "id", objectTypeId));
        obj.setObjectType(type);

        ItemCondition condition = itemConditionRepository.findById(itemConditionId)
                .orElseThrow(() -> new ResourceNotFoundException("ItemCondition", "id", itemConditionId));
        obj.setItemCondition(condition);
    }

}
