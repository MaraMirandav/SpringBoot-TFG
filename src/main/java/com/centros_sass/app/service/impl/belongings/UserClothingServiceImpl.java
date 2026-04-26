package com.centros_sass.app.service.impl.belongings;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.belongings.UserClothingRequestDTO;
import com.centros_sass.app.dto.belongings.UserClothingResponseDTO;
import com.centros_sass.app.dto.belongings.UserClothingUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.belongings.UserClothingMapper;
import com.centros_sass.app.model.belongings.UserClothing;
import com.centros_sass.app.model.catalogs.belongings.ClothingType;
import com.centros_sass.app.model.catalogs.belongings.ReturnReason;
import com.centros_sass.app.repository.catalogs.belongings.ClothingTypeRepository;
import com.centros_sass.app.repository.catalogs.belongings.ReturnReasonRepository;
import com.centros_sass.app.repository.belongings.UserClothingRepository;
import com.centros_sass.app.service.UserClothingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserClothingServiceImpl implements UserClothingService {

    private final UserClothingRepository userClothingRepository;
    private final ClothingTypeRepository clothingTypeRepository;
    private final ReturnReasonRepository returnReasonRepository;
    private final UserClothingMapper userClothingMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserClothingResponseDTO> findAll(Pageable pageable) {
        return userClothingRepository.findAllByIsActiveTrue(pageable)
                .map(userClothingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserClothingResponseDTO> findAllInactive(Pageable pageable) {
        return userClothingRepository.findAllByIsActiveFalse(pageable)
                .map(userClothingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserClothingResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return userClothingRepository.findAll(pageable)
                .map(userClothingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserClothingResponseDTO> findById(Integer id) {
        return userClothingRepository.findById(id)
                .map(userClothingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserClothingResponseDTO> findByClothingTypeId(Integer clothingTypeId, Pageable pageable) {
        return userClothingRepository.findAllByClothingTypeId(clothingTypeId, pageable)
                .map(userClothingMapper::toResponse);
    }

    @Override
    @Transactional
    public UserClothingResponseDTO save(UserClothingRequestDTO dto) {
        UserClothing clothing = userClothingMapper.toEntity(dto);
        assignRelations(clothing, dto.clothingTypeId(), dto.returnReasonId());
        UserClothing saved = userClothingRepository.save(clothing);
        return userClothingMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserClothingResponseDTO> update(Integer id, UserClothingUpdateDTO dto) {
        return userClothingRepository.findById(id).map(existing -> {
            if (dto.getClothingTypeId() != null) {
                ClothingType type = clothingTypeRepository.findById(dto.getClothingTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("ClothingType", "id", dto.getClothingTypeId()));
                existing.setClothingType(type);
            }
            if (dto.getReturnReasonId() != null) {
                ReturnReason reason = returnReasonRepository.findById(dto.getReturnReasonId())
                        .orElseThrow(() -> new ResourceNotFoundException("ReturnReason", "id", dto.getReturnReasonId()));
                existing.setReturnReason(reason);
            }

            userClothingMapper.updateFromDto(dto, existing);
            UserClothing saved = userClothingRepository.saveAndFlush(existing);
            return userClothingMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<UserClothingResponseDTO> delete(Integer id) {
        return userClothingRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            UserClothing saved = userClothingRepository.saveAndFlush(existing);
            return userClothingMapper.toResponse(saved);
        });
    }

    // Métodos Helpers

    private void assignRelations(UserClothing clothing, Integer clothingTypeId, Integer returnReasonId) {
        ClothingType type = clothingTypeRepository.findById(clothingTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("ClothingType", "id", clothingTypeId));
        clothing.setClothingType(type);

        ReturnReason reason = returnReasonRepository.findById(returnReasonId)
                .orElseThrow(() -> new ResourceNotFoundException("ReturnReason", "id", returnReasonId));
        clothing.setReturnReason(reason);
    }

}
