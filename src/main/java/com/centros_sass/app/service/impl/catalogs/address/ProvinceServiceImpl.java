package com.centros_sass.app.service.impl.catalogs.address;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.address.ProvinceRequestDTO;
import com.centros_sass.app.dto.catalogs.address.ProvinceResponseDTO;
import com.centros_sass.app.dto.catalogs.address.ProvinceUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.address.ProvinceMapper;
import com.centros_sass.app.model.catalogs.address.Province;
import com.centros_sass.app.repository.catalogs.address.ProvinceRepository;
import com.centros_sass.app.service.ProvinceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProvinceResponseDTO> findAll(Pageable pageable) {
        return provinceRepository.findAll(pageable)
                .map(provinceMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProvinceResponseDTO> findById(Integer id) {
        return provinceRepository.findById(id)
                .map(provinceMapper::toResponse);
    }

    @Override
    @Transactional
    public ProvinceResponseDTO save(ProvinceRequestDTO dto) {
        if (provinceRepository.existsByProvinceName(dto.provinceName())) {
            throw new BadRequestException("Ya existe un provincia con el nombre: " + dto.provinceName());
        }

        Province entity = provinceMapper.toEntity(dto);
        Province saved = provinceRepository.save(entity);
        return provinceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<ProvinceResponseDTO> update(Integer id, ProvinceUpdateDTO dto) {
        return provinceRepository.findById(id).map(existing -> {
            if (dto.getProvinceName() != null && !dto.getProvinceName().isBlank()) {
                boolean duplicado = provinceRepository.existsByProvinceNameAndIdNot(dto.getProvinceName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un provincia con el nombre: " + dto.getProvinceName());
                }
            }

            provinceMapper.updateFromDto(dto, existing);
            Province saved = provinceRepository.saveAndFlush(existing);
            return provinceMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Province entity = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Province", "id", id));

        if (!entity.getUserAddresses().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el provincia '" + entity.getProvinceName() + "' porque está en uso");
        }

        provinceRepository.delete(entity);
    }

}
