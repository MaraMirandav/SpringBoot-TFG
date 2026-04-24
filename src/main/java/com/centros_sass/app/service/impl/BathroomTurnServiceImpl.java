package com.centros_sass.app.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.bathroom.BathroomTurnRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomTurnResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomTurnUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.mapper.BathroomTurnMapper;
import com.centros_sass.app.model.bathroom.BathroomTurn;
import com.centros_sass.app.repository.BathroomTurnRepository;
import com.centros_sass.app.service.BathroomTurnService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BathroomTurnServiceImpl implements BathroomTurnService {

    private final BathroomTurnRepository bathroomTurnRepository;
    private final BathroomTurnMapper bathroomTurnMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BathroomTurnResponseDTO> findAll(Pageable pageable) {
        return bathroomTurnRepository.findAll(pageable)
                .map(bathroomTurnMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BathroomTurnResponseDTO> findById(Integer id) {
        return bathroomTurnRepository.findById(id)
                .map(bathroomTurnMapper::toResponse);
    }

    @Override
    @Transactional
    public BathroomTurnResponseDTO save(BathroomTurnRequestDTO dto) {
        if (bathroomTurnRepository.existsByTurnName(dto.turnName())) {
            throw new BadRequestException("Ya existe un turno de baño con el nombre: " + dto.turnName());
        }

        if (dto.startAt().isAfter(dto.endAt()) || dto.startAt().equals(dto.endAt())) {
            throw new BadRequestException("La hora de inicio debe ser anterior a la hora de fin");
        }

        BathroomTurn turn = bathroomTurnMapper.toEntity(dto);
        BathroomTurn saved = bathroomTurnRepository.save(turn);
        return bathroomTurnMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<BathroomTurnResponseDTO> update(Integer id, BathroomTurnUpdateDTO dto) {
        return bathroomTurnRepository.findById(id).map(existing -> {
            if (dto.getTurnName() != null && !dto.getTurnName().isBlank()) {
                boolean duplicado = bathroomTurnRepository.existsByTurnNameAndIdNot(dto.getTurnName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un turno de baño con el nombre: " + dto.getTurnName());
                }
            }

            if (dto.getStartAt() != null && dto.getEndAt() != null) {
                if (dto.getStartAt().isAfter(dto.getEndAt()) || dto.getStartAt().equals(dto.getEndAt())) {
                    throw new BadRequestException("La hora de inicio debe ser anterior a la hora de fin");
                }
            } else if (dto.getStartAt() != null && dto.getStartAt().isAfter(existing.getEndAt())) {
                throw new BadRequestException("La hora de inicio debe ser anterior a la hora de fin");
            } else if (dto.getEndAt() != null && dto.getEndAt().isBefore(existing.getStartAt())) {
                throw new BadRequestException("La hora de fin debe ser posterior a la hora de inicio");
            }

            bathroomTurnMapper.updateFromDto(dto, existing);
            BathroomTurn saved = bathroomTurnRepository.saveAndFlush(existing);
            return bathroomTurnMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<BathroomTurnResponseDTO> delete(Integer id) {
        return bathroomTurnRepository.findById(id).map(existing -> {
            bathroomTurnRepository.delete(existing);
            return bathroomTurnMapper.toResponse(existing);
        });
    }

}
