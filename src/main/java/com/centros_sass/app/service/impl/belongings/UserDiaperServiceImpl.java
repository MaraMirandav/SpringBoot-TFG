package com.centros_sass.app.service.impl.belongings;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.belongings.UserDiaperRequestDTO;
import com.centros_sass.app.dto.belongings.UserDiaperResponseDTO;
import com.centros_sass.app.dto.belongings.UserDiaperUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.belongings.UserDiaperMapper;
import com.centros_sass.app.model.belongings.UserDiaper;
import com.centros_sass.app.model.catalogs.belongings.DiaperSize;
import com.centros_sass.app.model.catalogs.belongings.DiaperType;
import com.centros_sass.app.repository.catalogs.belongings.DiaperSizeRepository;
import com.centros_sass.app.repository.catalogs.belongings.DiaperTypeRepository;
import com.centros_sass.app.repository.belongings.UserDiaperRepository;
import com.centros_sass.app.service.UserDiaperService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDiaperServiceImpl implements UserDiaperService {

    private final UserDiaperRepository userDiaperRepository;
    private final DiaperSizeRepository diaperSizeRepository;
    private final DiaperTypeRepository diaperTypeRepository;
    private final UserDiaperMapper userDiaperMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserDiaperResponseDTO> findAll(Pageable pageable) {
        return userDiaperRepository.findAllByIsActiveTrue(pageable)
                .map(userDiaperMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDiaperResponseDTO> findAllInactive(Pageable pageable) {
        return userDiaperRepository.findAllByIsActiveFalse(pageable)
                .map(userDiaperMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDiaperResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return userDiaperRepository.findAll(pageable)
                .map(userDiaperMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDiaperResponseDTO> findById(Integer id) {
        return userDiaperRepository.findById(id)
                .map(userDiaperMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDiaperResponseDTO> findByDiaperSizeId(Integer diaperSizeId, Pageable pageable) {
        return userDiaperRepository.findAllByDiaperSizeId(diaperSizeId, pageable)
                .map(userDiaperMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDiaperResponseDTO> findByDiaperTypeId(Integer diaperTypeId, Pageable pageable) {
        return userDiaperRepository.findAllByDiaperTypeId(diaperTypeId, pageable)
                .map(userDiaperMapper::toResponse);
    }

    @Override
    @Transactional
    public UserDiaperResponseDTO save(UserDiaperRequestDTO dto) {
        UserDiaper diaper = userDiaperMapper.toEntity(dto);
        assignRelations(diaper, dto.diaperSizeId(), dto.diaperTypeId());
        UserDiaper saved = userDiaperRepository.save(diaper);
        return userDiaperMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserDiaperResponseDTO> update(Integer id, UserDiaperUpdateDTO dto) {
        return userDiaperRepository.findById(id).map(existing -> {
            if (dto.getDiaperSizeId() != null) {
                DiaperSize size = diaperSizeRepository.findById(dto.getDiaperSizeId())
                        .orElseThrow(() -> new ResourceNotFoundException("DiaperSize", "id", dto.getDiaperSizeId()));
                existing.setDiaperSize(size);
            }
            if (dto.getDiaperTypeId() != null) {
                DiaperType type = diaperTypeRepository.findById(dto.getDiaperTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("DiaperType", "id", dto.getDiaperTypeId()));
                existing.setDiaperType(type);
            }

            userDiaperMapper.updateFromDto(dto, existing);
            UserDiaper saved = userDiaperRepository.saveAndFlush(existing);
            return userDiaperMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<UserDiaperResponseDTO> delete(Integer id) {
        return userDiaperRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            UserDiaper saved = userDiaperRepository.saveAndFlush(existing);
            return userDiaperMapper.toResponse(saved);
        });
    }

    // Métodos Helpers

    private void assignRelations(UserDiaper diaper, Integer diaperSizeId, Integer diaperTypeId) {
        DiaperSize size = diaperSizeRepository.findById(diaperSizeId)
                .orElseThrow(() -> new ResourceNotFoundException("DiaperSize", "id", diaperSizeId));
        diaper.setDiaperSize(size);

        DiaperType type = diaperTypeRepository.findById(diaperTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DiaperType", "id", diaperTypeId));
        diaper.setDiaperType(type);
    }

}
