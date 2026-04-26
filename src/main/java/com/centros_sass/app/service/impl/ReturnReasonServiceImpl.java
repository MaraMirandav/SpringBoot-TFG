package com.centros_sass.app.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.belongings.ReturnReasonRequestDTO;
import com.centros_sass.app.dto.belongings.ReturnReasonResponseDTO;
import com.centros_sass.app.dto.belongings.ReturnReasonUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.mapper.ReturnReasonMapper;
import com.centros_sass.app.model.catalogs.dynamic.belongings.ReturnReason;
import com.centros_sass.app.repository.ReturnReasonRepository;
import com.centros_sass.app.service.ReturnReasonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReturnReasonServiceImpl implements ReturnReasonService {

    private final ReturnReasonRepository returnReasonRepository;
    private final ReturnReasonMapper returnReasonMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ReturnReasonResponseDTO> findAll(Pageable pageable) {
        return returnReasonRepository.findAllByIsActiveTrue(pageable)
                .map(returnReasonMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReturnReasonResponseDTO> findAllInactive(Pageable pageable) {
        return returnReasonRepository.findAllByIsActiveFalse(pageable)
                .map(returnReasonMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReturnReasonResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return returnReasonRepository.findAll(pageable)
                .map(returnReasonMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReturnReasonResponseDTO> findById(Integer id) {
        return returnReasonRepository.findById(id)
                .map(returnReasonMapper::toResponse);
    }

    @Override
    @Transactional
    public ReturnReasonResponseDTO save(ReturnReasonRequestDTO dto) {
        if (returnReasonRepository.existsByReason(dto.reason())) {
            throw new BadRequestException("Ya existe una razón de devolución con el nombre: " + dto.reason());
        }

        ReturnReason reason = returnReasonMapper.toEntity(dto);
        ReturnReason saved = returnReasonRepository.save(reason);
        return returnReasonMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<ReturnReasonResponseDTO> update(Integer id, ReturnReasonUpdateDTO dto) {
        return returnReasonRepository.findById(id).map(existing -> {
            if (dto.getReason() != null && !dto.getReason().isBlank()) {
                boolean duplicado = returnReasonRepository.existsByReasonAndIdNot(dto.getReason(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe una razón de devolución con el nombre: " + dto.getReason());
                }
            }

            returnReasonMapper.updateFromDto(dto, existing);
            ReturnReason saved = returnReasonRepository.saveAndFlush(existing);
            return returnReasonMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<ReturnReasonResponseDTO> delete(Integer id) {
        return returnReasonRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            ReturnReason saved = returnReasonRepository.saveAndFlush(existing);
            return returnReasonMapper.toResponse(saved);
        });
    }

}
