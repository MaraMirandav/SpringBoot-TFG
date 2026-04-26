package com.centros_sass.app.service.impl.catalogs.address;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.address.RegionRequestDTO;
import com.centros_sass.app.dto.catalogs.address.RegionResponseDTO;
import com.centros_sass.app.dto.catalogs.address.RegionUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.address.RegionMapper;
import com.centros_sass.app.model.catalogs.address.Region;
import com.centros_sass.app.repository.catalogs.address.RegionRepository;
import com.centros_sass.app.service.RegionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RegionResponseDTO> findAll(Pageable pageable) {
        return regionRepository.findAll(pageable)
                .map(regionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegionResponseDTO> findById(Integer id) {
        return regionRepository.findById(id)
                .map(regionMapper::toResponse);
    }

    @Override
    @Transactional
    public RegionResponseDTO save(RegionRequestDTO dto) {
        if (regionRepository.existsByRegionName(dto.regionName())) {
            throw new BadRequestException("Ya existe un región con el nombre: " + dto.regionName());
        }

        Region entity = regionMapper.toEntity(dto);
        Region saved = regionRepository.save(entity);
        return regionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<RegionResponseDTO> update(Integer id, RegionUpdateDTO dto) {
        return regionRepository.findById(id).map(existing -> {
            if (dto.getRegionName() != null && !dto.getRegionName().isBlank()) {
                boolean duplicado = regionRepository.existsByRegionNameAndIdNot(dto.getRegionName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un región con el nombre: " + dto.getRegionName());
                }
            }

            regionMapper.updateFromDto(dto, existing);
            Region saved = regionRepository.saveAndFlush(existing);
            return regionMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Region entity = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region", "id", id));

        if (!entity.getUserAddresses().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el región '" + entity.getRegionName() + "' porque está en uso");
        }

        regionRepository.delete(entity);
    }

}
