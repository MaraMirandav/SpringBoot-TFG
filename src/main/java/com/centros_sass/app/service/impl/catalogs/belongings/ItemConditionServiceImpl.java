package com.centros_sass.app.service.impl.catalogs.belongings;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.belongings.ItemConditionRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ItemConditionResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ItemConditionUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.belongings.ItemConditionMapper;
import com.centros_sass.app.model.catalogs.belongings.ItemCondition;
import com.centros_sass.app.repository.catalogs.belongings.ItemConditionRepository;
import com.centros_sass.app.service.ItemConditionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemConditionServiceImpl implements ItemConditionService {

    private final ItemConditionRepository itemConditionRepository;
    private final ItemConditionMapper itemConditionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ItemConditionResponseDTO> findAll(Pageable pageable) {
        return itemConditionRepository.findAll(pageable)
                .map(itemConditionMapper::toResponse);
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<ItemConditionResponseDTO> findById(Integer id) {
        return itemConditionRepository.findById(id)
                .map(itemConditionMapper::toResponse);
    }

    @Override
    @Transactional
    public ItemConditionResponseDTO save(ItemConditionRequestDTO dto) {
        if (itemConditionRepository.existsByConditionName(dto.conditionName())) {
            throw new BadRequestException("Ya existe una condición con el nombre: " + dto.conditionName());
        }

        ItemCondition condition = itemConditionMapper.toEntity(dto);
        ItemCondition saved = itemConditionRepository.save(condition);
        return itemConditionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<ItemConditionResponseDTO> update(Integer id, ItemConditionUpdateDTO dto) {
        return itemConditionRepository.findById(id).map(existing -> {
            if (dto.getConditionName() != null && !dto.getConditionName().isBlank()) {
                boolean duplicado = itemConditionRepository.existsByConditionNameAndIdNot(dto.getConditionName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe una condición con el nombre: " + dto.getConditionName());
                }
            }

            itemConditionMapper.updateFromDto(dto, existing);
            ItemCondition saved = itemConditionRepository.saveAndFlush(existing);
            return itemConditionMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        ItemCondition entity = itemConditionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemCondition", "id", id));

        if (!entity.getUserObjects().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el condición '" + entity.getConditionName() + "' porque está en uso");
        }

        itemConditionRepository.delete(entity);
    }

}
