package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.belongings.ItemConditionRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ItemConditionResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ItemConditionUpdateDTO;

public interface ItemConditionService {

    Page<ItemConditionResponseDTO> findAll(Pageable pageable);

    Optional<ItemConditionResponseDTO> findById(Integer id);

    ItemConditionResponseDTO save(ItemConditionRequestDTO dto);

    Optional<ItemConditionResponseDTO> update(Integer id, ItemConditionUpdateDTO dto);

    void delete(Integer id);

}
