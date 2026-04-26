package com.centros_sass.app.service.impl.catalogs.address;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.address.CityRequestDTO;
import com.centros_sass.app.dto.catalogs.address.CityResponseDTO;
import com.centros_sass.app.dto.catalogs.address.CityUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.address.CityMapper;
import com.centros_sass.app.model.catalogs.address.City;
import com.centros_sass.app.repository.catalogs.address.CityRepository;
import com.centros_sass.app.service.CityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponseDTO> findAll(Pageable pageable) {
        return cityRepository.findAll(pageable)
                .map(cityMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityResponseDTO> findById(Integer id) {
        return cityRepository.findById(id)
                .map(cityMapper::toResponse);
    }

    @Override
    @Transactional
    public CityResponseDTO save(CityRequestDTO dto) {
        if (cityRepository.existsByCityName(dto.cityName())) {
            throw new BadRequestException("Ya existe un ciudad con el nombre: " + dto.cityName());
        }

        City entity = cityMapper.toEntity(dto);
        City saved = cityRepository.save(entity);
        return cityMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<CityResponseDTO> update(Integer id, CityUpdateDTO dto) {
        return cityRepository.findById(id).map(existing -> {
            if (dto.getCityName() != null && !dto.getCityName().isBlank()) {
                boolean duplicado = cityRepository.existsByCityNameAndIdNot(dto.getCityName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un ciudad con el nombre: " + dto.getCityName());
                }
            }

            cityMapper.updateFromDto(dto, existing);
            City saved = cityRepository.saveAndFlush(existing);
            return cityMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        City entity = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", id));

        if (!entity.getUserAddresses().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el ciudad '" + entity.getCityName() + "' porque está en uso");
        }

        cityRepository.delete(entity);
    }

}
