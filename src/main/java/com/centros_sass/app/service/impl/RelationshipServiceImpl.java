package com.centros_sass.app.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.people.RelationshipRequestDTO;
import com.centros_sass.app.dto.people.RelationshipResponseDTO;
import com.centros_sass.app.dto.people.RelationshipUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.RelationshipMapper;
import com.centros_sass.app.model.catalogs.dynamic.people.Relationship;
import com.centros_sass.app.repository.RelationshipRepository;
import com.centros_sass.app.service.RelationshipService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelationshipServiceImpl implements RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final RelationshipMapper relationshipMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RelationshipResponseDTO> findAll(Pageable pageable) {
        return relationshipRepository.findAll(pageable)
                .map(relationshipMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RelationshipResponseDTO> findById(Integer id) {
        return relationshipRepository.findById(id)
                .map(relationshipMapper::toResponse);
    }

    @Override
    @Transactional
    public RelationshipResponseDTO save(RelationshipRequestDTO dto) {
        if (relationshipRepository.existsByRelationshipName(dto.relationshipName())) {
            throw new BadRequestException("Ya existe una relación con el nombre: " + dto.relationshipName());
        }

        Relationship relationship = relationshipMapper.toEntity(dto);
        Relationship saved = relationshipRepository.save(relationship);
        return relationshipMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<RelationshipResponseDTO> update(Integer id, RelationshipUpdateDTO dto) {
        return relationshipRepository.findById(id).map(existing -> {
            if (dto.getRelationshipName() != null && !dto.getRelationshipName().isBlank()) {
                boolean duplicado = relationshipRepository.existsByRelationshipNameAndIdNot(dto.getRelationshipName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe una relación con el nombre: " + dto.getRelationshipName());
                }
            }

            relationshipMapper.updateFromDto(dto, existing);
            Relationship saved = relationshipRepository.saveAndFlush(existing);
            return relationshipMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Relationship relationship = relationshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", id));

        if (!relationship.getUserContacts().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar la relación '" + relationship.getRelationshipName() + "' porque está en uso por " + relationship.getUserContacts().size() + " contacto(s)");
        }

        relationshipRepository.delete(relationship);
    }

}
