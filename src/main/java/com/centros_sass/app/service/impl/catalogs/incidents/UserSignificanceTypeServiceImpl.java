/*
package com.centros_sass.app.service.impl.catalogs.incidents;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.incidents.UserSignificanceTypeMapper;
import com.centros_sass.app.model.catalogs.incidents.user.UserSignificanceType;
import com.centros_sass.app.repository.catalogs.incidents.UserSignificanceTypeRepository;
import com.centros_sass.app.service.UserSignificanceTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSignificanceTypeServiceImpl implements UserSignificanceTypeService {

    private final UserSignificanceTypeRepository userSignificanceTypeRepository;
    private final UserSignificanceTypeMapper userSignificanceTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserSignificanceTypeResponseDTO> findAll(Pageable pageable) {
        return userSignificanceTypeRepository.findAll(pageable)
                .map(userSignificanceTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserSignificanceTypeResponseDTO> findById(Integer id) {
        return userSignificanceTypeRepository.findById(id)
                .map(userSignificanceTypeMapper::toResponse);
    }

    @Override
    @Transactional
    public UserSignificanceTypeResponseDTO save(UserSignificanceTypeRequestDTO dto) {
        if (userSignificanceTypeRepository.existsBySignificanceName(dto.significanceName())) {
            throw new BadRequestException("Ya existe un tipo de significancia de usuario con el nombre: " + dto.significanceName());
        }

        UserSignificanceType entity = userSignificanceTypeMapper.toEntity(dto);
        UserSignificanceType saved = userSignificanceTypeRepository.save(entity);
        return userSignificanceTypeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserSignificanceTypeResponseDTO> update(Integer id, UserSignificanceTypeUpdateDTO dto) {
        return userSignificanceTypeRepository.findById(id).map(existing -> {
            if (dto.getSignificanceName() != null && !dto.getSignificanceName().isBlank()) {
                boolean duplicado = userSignificanceTypeRepository.existsBySignificanceNameAndIdNot(dto.getSignificanceName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un tipo de significancia de usuario con el nombre: " + dto.getSignificanceName());
                }
            }

            userSignificanceTypeMapper.updateFromDto(dto, existing);
            UserSignificanceType saved = userSignificanceTypeRepository.saveAndFlush(existing);
            return userSignificanceTypeMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        UserSignificanceType entity = userSignificanceTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserSignificanceType", "id", id));

        if (!entity.getUserIncidents().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el tipo de significancia de usuario '" + entity.getSignificanceName() + "' porque está en uso");
        }

        userSignificanceTypeRepository.delete(entity);
    }

}

*/
