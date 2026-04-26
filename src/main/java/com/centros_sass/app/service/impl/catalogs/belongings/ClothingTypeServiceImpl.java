package com.centros_sass.app.service.impl.catalogs.belongings;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.belongings.ClothingTypeMapper;
import com.centros_sass.app.model.catalogs.belongings.ClothingType;
import com.centros_sass.app.repository.catalogs.belongings.ClothingTypeRepository;
import com.centros_sass.app.service.ClothingTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClothingTypeServiceImpl implements ClothingTypeService {

    private final ClothingTypeRepository clothingTypeRepository;
    private final ClothingTypeMapper clothingTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ClothingTypeResponseDTO> findAll(Pageable pageable) {
        return clothingTypeRepository.findAll(pageable)
                .map(clothingTypeMapper::toResponse);
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<ClothingTypeResponseDTO> findById(Integer id) {
        return clothingTypeRepository.findById(id)
                .map(clothingTypeMapper::toResponse);
    }

    @Override
    @Transactional
    public ClothingTypeResponseDTO save(ClothingTypeRequestDTO dto) {
        if (clothingTypeRepository.existsByClothingName(dto.clothingName())) {
            throw new BadRequestException("Ya existe un tipo de prenda con el nombre: " + dto.clothingName());
        }

        ClothingType type = clothingTypeMapper.toEntity(dto);
        ClothingType saved = clothingTypeRepository.save(type);
        return clothingTypeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<ClothingTypeResponseDTO> update(Integer id, ClothingTypeUpdateDTO dto) {
        return clothingTypeRepository.findById(id).map(existing -> {
            if (dto.getClothingName() != null && !dto.getClothingName().isBlank()) {
                boolean duplicado = clothingTypeRepository.existsByClothingNameAndIdNot(dto.getClothingName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un tipo de prenda con el nombre: " + dto.getClothingName());
                }
            }

            clothingTypeMapper.updateFromDto(dto, existing);
            ClothingType saved = clothingTypeRepository.saveAndFlush(existing);
            return clothingTypeMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        ClothingType entity = clothingTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClothingType", "id", id));

        if (!entity.getUserClothings().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el tipo de prenda '" + entity.getClothingName() + "' porque está en uso");
        }

        clothingTypeRepository.delete(entity);
    }

}
