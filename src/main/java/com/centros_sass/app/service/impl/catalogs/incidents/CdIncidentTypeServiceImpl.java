package com.centros_sass.app.service.impl.catalogs.incidents;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.incidents.CdIncidentTypeMapper;
import com.centros_sass.app.model.catalogs.incidents.center.CdIncidentType;
import com.centros_sass.app.repository.catalogs.incidents.CdIncidentTypeRepository;
import com.centros_sass.app.service.CdIncidentTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CdIncidentTypeServiceImpl implements CdIncidentTypeService {

    private final CdIncidentTypeRepository cdIncidentTypeRepository;
    private final CdIncidentTypeMapper cdIncidentTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CdIncidentTypeResponseDTO> findAll(Pageable pageable) {
        return cdIncidentTypeRepository.findAll(pageable)
                .map(cdIncidentTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CdIncidentTypeResponseDTO> findById(Integer id) {
        return cdIncidentTypeRepository.findById(id)
                .map(cdIncidentTypeMapper::toResponse);
    }

    @Override
    @Transactional
    public CdIncidentTypeResponseDTO save(CdIncidentTypeRequestDTO dto) {
        if (cdIncidentTypeRepository.existsByIncidentName(dto.incidentName())) {
            throw new BadRequestException("Ya existe un tipo de incidencia del centro con el nombre: " + dto.incidentName());
        }

        CdIncidentType entity = cdIncidentTypeMapper.toEntity(dto);
        CdIncidentType saved = cdIncidentTypeRepository.save(entity);
        return cdIncidentTypeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<CdIncidentTypeResponseDTO> update(Integer id, CdIncidentTypeUpdateDTO dto) {
        return cdIncidentTypeRepository.findById(id).map(existing -> {
            if (dto.getIncidentName() != null && !dto.getIncidentName().isBlank()) {
                boolean duplicado = cdIncidentTypeRepository.existsByIncidentNameAndIdNot(dto.getIncidentName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un tipo de incidencia del centro con el nombre: " + dto.getIncidentName());
                }
            }

            cdIncidentTypeMapper.updateFromDto(dto, existing);
            CdIncidentType saved = cdIncidentTypeRepository.saveAndFlush(existing);
            return cdIncidentTypeMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        CdIncidentType entity = cdIncidentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CdIncidentType", "id", id));

        if (!entity.getCenterIncidents().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el tipo de incidencia del centro '" + entity.getIncidentName() + "' porque está en uso");
        }

        cdIncidentTypeRepository.delete(entity);
    }

}
