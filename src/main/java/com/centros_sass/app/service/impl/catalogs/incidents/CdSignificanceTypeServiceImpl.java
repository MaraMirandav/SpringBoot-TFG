package com.centros_sass.app.service.impl.catalogs.incidents;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.incidents.CdSignificanceTypeMapper;
import com.centros_sass.app.model.catalogs.incidents.center.CdSignificanceType;
import com.centros_sass.app.repository.catalogs.incidents.CdSignificanceTypeRepository;
import com.centros_sass.app.service.CdSignificanceTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CdSignificanceTypeServiceImpl implements CdSignificanceTypeService {

    private final CdSignificanceTypeRepository cdSignificanceTypeRepository;
    private final CdSignificanceTypeMapper cdSignificanceTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CdSignificanceTypeResponseDTO> findAll(Pageable pageable) {
        return cdSignificanceTypeRepository.findAll(pageable)
                .map(cdSignificanceTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CdSignificanceTypeResponseDTO> findById(Integer id) {
        return cdSignificanceTypeRepository.findById(id)
                .map(cdSignificanceTypeMapper::toResponse);
    }

    @Override
    @Transactional
    public CdSignificanceTypeResponseDTO save(CdSignificanceTypeRequestDTO dto) {
        if (cdSignificanceTypeRepository.existsBySignificanceName(dto.significanceName())) {
            throw new BadRequestException("Ya existe un tipo de significancia del centro con el nombre: " + dto.significanceName());
        }

        CdSignificanceType entity = cdSignificanceTypeMapper.toEntity(dto);
        CdSignificanceType saved = cdSignificanceTypeRepository.save(entity);
        return cdSignificanceTypeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<CdSignificanceTypeResponseDTO> update(Integer id, CdSignificanceTypeUpdateDTO dto) {
        return cdSignificanceTypeRepository.findById(id).map(existing -> {
            if (dto.getSignificanceName() != null && !dto.getSignificanceName().isBlank()) {
                boolean duplicado = cdSignificanceTypeRepository.existsBySignificanceNameAndIdNot(dto.getSignificanceName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un tipo de significancia del centro con el nombre: " + dto.getSignificanceName());
                }
            }

            cdSignificanceTypeMapper.updateFromDto(dto, existing);
            CdSignificanceType saved = cdSignificanceTypeRepository.saveAndFlush(existing);
            return cdSignificanceTypeMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        CdSignificanceType entity = cdSignificanceTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CdSignificanceType", "id", id));

        if (!entity.getCenterIncidents().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el tipo de significancia del centro '" + entity.getSignificanceName() + "' porque está en uso");
        }

        cdSignificanceTypeRepository.delete(entity);
    }

}
