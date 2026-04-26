package com.centros_sass.app.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.people.SexRequestDTO;
import com.centros_sass.app.dto.people.SexResponseDTO;
import com.centros_sass.app.dto.people.SexUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.SexMapper;
import com.centros_sass.app.model.catalogs.dynamic.people.Sex;
import com.centros_sass.app.repository.SexRepository;
import com.centros_sass.app.service.SexService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SexServiceImpl implements SexService {

    private final SexRepository sexRepository;
    private final SexMapper sexMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<SexResponseDTO> findAll(Pageable pageable) {
        return sexRepository.findAll(pageable)
                .map(sexMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SexResponseDTO> findById(Integer id) {
        return sexRepository.findById(id)
                .map(sexMapper::toResponse);
    }

    @Override
    @Transactional
    public SexResponseDTO save(SexRequestDTO dto) {
        if (sexRepository.existsBySex(dto.sex())) {
            throw new BadRequestException("Ya existe un sexo con el nombre: " + dto.sex());
        }

        Sex sex = sexMapper.toEntity(dto);
        Sex saved = sexRepository.save(sex);
        return sexMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<SexResponseDTO> update(Integer id, SexUpdateDTO dto) {
        return sexRepository.findById(id).map(existing -> {
            if (dto.getSex() != null && !dto.getSex().isBlank()) {
                boolean duplicado = sexRepository.existsBySexAndIdNot(dto.getSex(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un sexo con el nombre: " + dto.getSex());
                }
            }

            sexMapper.updateFromDto(dto, existing);
            Sex saved = sexRepository.saveAndFlush(existing);
            return sexMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Sex sex = sexRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sex", "id", id));

        if (!sex.getUsers().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el sexo '" + sex.getSex() + "' porque está en uso por " + sex.getUsers().size() + " usuario(s)");
        }

        sexRepository.delete(sex);
    }

}
