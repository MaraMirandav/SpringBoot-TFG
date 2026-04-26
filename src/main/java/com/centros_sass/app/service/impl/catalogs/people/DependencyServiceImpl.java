package com.centros_sass.app.service.impl.catalogs.people;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.people.DependencyRequestDTO;
import com.centros_sass.app.dto.catalogs.people.DependencyResponseDTO;
import com.centros_sass.app.dto.catalogs.people.DependencyUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.people.DependencyMapper;
import com.centros_sass.app.model.catalogs.people.Dependency;
import com.centros_sass.app.repository.catalogs.people.DependencyRepository;
import com.centros_sass.app.service.DependencyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DependencyServiceImpl implements DependencyService {

    private final DependencyRepository dependencyRepository;
    private final DependencyMapper dependencyMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<DependencyResponseDTO> findAll(Pageable pageable) {
        return dependencyRepository.findAll(pageable)
                .map(dependencyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DependencyResponseDTO> findById(Integer id) {
        return dependencyRepository.findById(id)
                .map(dependencyMapper::toResponse);
    }

    @Override
    @Transactional
    public DependencyResponseDTO save(DependencyRequestDTO dto) {
        if (dependencyRepository.existsByDependencyLevel(dto.dependencyLevel())) {
            throw new BadRequestException("Ya existe un nivel de dependencia con el nombre: " + dto.dependencyLevel());
        }

        Dependency dependency = dependencyMapper.toEntity(dto);
        Dependency saved = dependencyRepository.save(dependency);
        return dependencyMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<DependencyResponseDTO> update(Integer id, DependencyUpdateDTO dto) {
        return dependencyRepository.findById(id).map(existing -> {
            if (dto.getDependencyLevel() != null && !dto.getDependencyLevel().isBlank()) {
                boolean duplicado = dependencyRepository.existsByDependencyLevelAndIdNot(dto.getDependencyLevel(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un nivel de dependencia con el nombre: " + dto.getDependencyLevel());
                }
            }

            dependencyMapper.updateFromDto(dto, existing);
            Dependency saved = dependencyRepository.saveAndFlush(existing);
            return dependencyMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Dependency dependency = dependencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dependency", "id", id));

        if (!dependency.getUsers().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el nivel de dependencia '" + dependency.getDependencyLevel() + "' porque está en uso por " + dependency.getUsers().size() + " usuario(s)");
        }

        dependencyRepository.delete(dependency);
    }

}
