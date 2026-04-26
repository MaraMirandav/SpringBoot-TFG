package com.centros_sass.app.service.impl.catalogs.bathroom;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.bathroom.BathroomTaskRequestDTO;
import com.centros_sass.app.dto.catalogs.bathroom.BathroomTaskResponseDTO;
import com.centros_sass.app.dto.catalogs.bathroom.BathroomTaskUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.bathroom.BathroomTaskMapper;
import com.centros_sass.app.model.catalogs.bathroom.BathroomTask;
import com.centros_sass.app.repository.catalogs.bathroom.BathroomTaskRepository;
import com.centros_sass.app.service.BathroomTaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BathroomTaskServiceImpl implements BathroomTaskService {

    private final BathroomTaskRepository bathroomTaskRepository;
    private final BathroomTaskMapper bathroomTaskMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BathroomTaskResponseDTO> findAll(Pageable pageable) {
        return bathroomTaskRepository.findAll(pageable)
                .map(bathroomTaskMapper::toResponse);
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<BathroomTaskResponseDTO> findById(Integer id) {
        return bathroomTaskRepository.findById(id)
                .map(bathroomTaskMapper::toResponse);
    }

    @Override
    @Transactional
    public BathroomTaskResponseDTO save(BathroomTaskRequestDTO dto) {
        if (bathroomTaskRepository.existsByTaskName(dto.taskName())) {
            throw new BadRequestException("Ya existe una tarea de higiene con el nombre: " + dto.taskName());
        }

        BathroomTask task = bathroomTaskMapper.toEntity(dto);
        BathroomTask saved = bathroomTaskRepository.save(task);
        return bathroomTaskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<BathroomTaskResponseDTO> update(Integer id, BathroomTaskUpdateDTO dto) {
        return bathroomTaskRepository.findById(id).map(existing -> {
            if (dto.getTaskName() != null && !dto.getTaskName().isBlank()) {
                boolean duplicado = bathroomTaskRepository.existsByTaskNameAndIdNot(dto.getTaskName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe una tarea de higiene con el nombre: " + dto.getTaskName());
                }
            }

            bathroomTaskMapper.updateFromDto(dto, existing);
            BathroomTask saved = bathroomTaskRepository.saveAndFlush(existing);
            return bathroomTaskMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        BathroomTask entity = bathroomTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomTask", "id", id));

        if (!entity.getBathroomSchedules().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el tarea de higiene '" + entity.getTaskName() + "' porque está en uso");
        }

        bathroomTaskRepository.delete(entity);
    }

}
