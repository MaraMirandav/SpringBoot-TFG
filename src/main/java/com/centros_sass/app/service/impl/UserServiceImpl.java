package com.centros_sass.app.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.user.UserRequestDTO;
import com.centros_sass.app.dto.user.UserResponseDTO;
import com.centros_sass.app.dto.user.UserUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.UserMapper;
import com.centros_sass.app.model.catalogs.fixed.people.Dependency;
import com.centros_sass.app.model.catalogs.fixed.people.Sex;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.repository.DependencyRepository;
import com.centros_sass.app.repository.SexRepository;
import com.centros_sass.app.repository.UserRepository;
import com.centros_sass.app.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SexRepository sexRepository;
    private final DependencyRepository dependencyRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAllByIsActiveTrue(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAllIncludingInactive(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponseDTO save(UserRequestDTO dto) {
        if (userRepository.existsByDni(dto.dni())) {
            throw new BadRequestException("El DNI/NIE ya está registrado");
        }

        Sex sex = sexRepository.findById(dto.sexId())
                .orElseThrow(() -> new ResourceNotFoundException("Sex", "id", dto.sexId()));

        Dependency dependency = dependencyRepository.findById(dto.dependencyId())
                .orElseThrow(() -> new ResourceNotFoundException("Dependency", "id", dto.dependencyId()));

        User user = userMapper.toEntity(dto);
        user.setSex(sex);
        user.setDependency(dependency);

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserResponseDTO> update(Integer id, UserUpdateDTO dto) {
        return userRepository.findById(id).map(existing -> {
            if (dto.getDni() != null) {
                String trimmedDni = dto.getDni().trim();
                if (!trimmedDni.equals(existing.getDni())
                        && userRepository.existsByDni(trimmedDni)) {
                    throw new BadRequestException("El DNI/NIE ya está registrado");
                }
                existing.setDni(trimmedDni);
            }

            if (dto.getSexId() != null) {
                Sex sex = sexRepository.findById(dto.getSexId())
                        .orElseThrow(() -> new ResourceNotFoundException("Sex", "id", dto.getSexId()));
                existing.setSex(sex);
            }

            if (dto.getDependencyId() != null) {
                Dependency dependency = dependencyRepository.findById(dto.getDependencyId())
                        .orElseThrow(() -> new ResourceNotFoundException("Dependency", "id", dto.getDependencyId()));
                existing.setDependency(dependency);
            }

            userMapper.updateFromDto(dto, existing);

            User saved = userRepository.saveAndFlush(existing);
            return userMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<UserResponseDTO> delete(Integer id) {
        return userRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            User saved = userRepository.saveAndFlush(existing);
            return userMapper.toResponse(saved);
        });
    }

}
