/*
package com.centros_sass.app.service.impl.catalogs.incidents;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.incidents.UserIncidentTypeMapper;
import com.centros_sass.app.model.catalogs.incidents.user.UserIncidentType;
import com.centros_sass.app.repository.catalogs.incidents.UserIncidentTypeRepository;
import com.centros_sass.app.service.UserIncidentTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserIncidentTypeServiceImpl implements UserIncidentTypeService {

    private final UserIncidentTypeRepository userIncidentTypeRepository;
    private final UserIncidentTypeMapper userIncidentTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserIncidentTypeResponseDTO> findAll(Pageable pageable) {
        return userIncidentTypeRepository.findAll(pageable)
                .map(userIncidentTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserIncidentTypeResponseDTO> findById(Integer id) {
        return userIncidentTypeRepository.findById(id)
                .map(userIncidentTypeMapper::toResponse);
    }

    @Override
    @Transactional
    public UserIncidentTypeResponseDTO save(UserIncidentTypeRequestDTO dto) {
        if (userIncidentTypeRepository.existsByIncidentName(dto.incidentName())) {
            throw new BadRequestException("Ya existe un tipo de incidencia de usuario con el nombre: " + dto.incidentName());
        }

        UserIncidentType entity = userIncidentTypeMapper.toEntity(dto);
        UserIncidentType saved = userIncidentTypeRepository.save(entity);
        return userIncidentTypeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserIncidentTypeResponseDTO> update(Integer id, UserIncidentTypeUpdateDTO dto) {
        return userIncidentTypeRepository.findById(id).map(existing -> {
            if (dto.getIncidentName() != null && !dto.getIncidentName().isBlank()) {
                boolean duplicado = userIncidentTypeRepository.existsByIncidentNameAndIdNot(dto.getIncidentName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un tipo de incidencia de usuario con el nombre: " + dto.getIncidentName());
                }
            }

            userIncidentTypeMapper.updateFromDto(dto, existing);
            UserIncidentType saved = userIncidentTypeRepository.saveAndFlush(existing);
            return userIncidentTypeMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        UserIncidentType entity = userIncidentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserIncidentType", "id", id));

        if (!entity.getUserIncidents().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el tipo de incidencia de usuario '" + entity.getIncidentName() + "' porque está en uso");
        }

        userIncidentTypeRepository.delete(entity);
    }

}

*/
