package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.people.RelationshipRequestDTO;
import com.centros_sass.app.dto.people.RelationshipResponseDTO;
import com.centros_sass.app.dto.people.RelationshipUpdateDTO;

public interface RelationshipService {

    Page<RelationshipResponseDTO> findAll(Pageable pageable);

    Optional<RelationshipResponseDTO> findById(Integer id);

    RelationshipResponseDTO save(RelationshipRequestDTO dto);

    Optional<RelationshipResponseDTO> update(Integer id, RelationshipUpdateDTO dto);

    void delete(Integer id);

}
