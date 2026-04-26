package com.centros_sass.app.service.impl.catalogs.calendar;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.calendar.OpenDayRequestDTO;
import com.centros_sass.app.dto.catalogs.calendar.OpenDayResponseDTO;
import com.centros_sass.app.dto.catalogs.calendar.OpenDayUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.calendar.OpenDayMapper;
import com.centros_sass.app.model.catalogs.calendar.OpenDay;
import com.centros_sass.app.repository.catalogs.calendar.OpenDayRepository;
import com.centros_sass.app.service.OpenDayService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenDayServiceImpl implements OpenDayService {

    private final OpenDayRepository openDayRepository;
    private final OpenDayMapper openDayMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<OpenDayResponseDTO> findAll(Pageable pageable) {
        return openDayRepository.findAll(pageable)
                .map(openDayMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OpenDayResponseDTO> findById(Integer id) {
        return openDayRepository.findById(id)
                .map(openDayMapper::toResponse);
    }

    @Override
    @Transactional
    public OpenDayResponseDTO save(OpenDayRequestDTO dto) {
        if (openDayRepository.existsByDayName(dto.dayName())) {
            throw new BadRequestException("Ya existe un día abierto con el nombre: " + dto.dayName());
        }

        OpenDay entity = openDayMapper.toEntity(dto);
        OpenDay saved = openDayRepository.save(entity);
        return openDayMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<OpenDayResponseDTO> update(Integer id, OpenDayUpdateDTO dto) {
        return openDayRepository.findById(id).map(existing -> {
            if (dto.getDayName() != null && !dto.getDayName().isBlank()) {
                boolean duplicado = openDayRepository.existsByDayNameAndIdNot(dto.getDayName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un día abierto con el nombre: " + dto.getDayName());
                }
            }

            openDayMapper.updateFromDto(dto, existing);
            OpenDay saved = openDayRepository.saveAndFlush(existing);
            return openDayMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        OpenDay entity = openDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OpenDay", "id", id));

        if (!entity.getSchedules().isEmpty() || !entity.getAttendanceDays().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el día '" + entity.getDayName() + "' porque está en uso");
        }

        openDayRepository.delete(entity);
    }

}
