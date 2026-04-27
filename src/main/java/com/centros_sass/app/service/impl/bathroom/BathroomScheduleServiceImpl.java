package com.centros_sass.app.service.impl.bathroom;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.bathroom.BathroomScheduleRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomScheduleResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomScheduleUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.bathroom.BathroomScheduleMapper;
import com.centros_sass.app.model.bathroom.BathroomSchedule;
import com.centros_sass.app.model.bathroom.BathroomTurn;
import com.centros_sass.app.model.catalogs.bathroom.BathroomTask;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.repository.bathroom.BathroomScheduleRepository;
import com.centros_sass.app.repository.bathroom.BathroomTurnRepository;
import com.centros_sass.app.repository.catalogs.bathroom.BathroomTaskRepository;
import com.centros_sass.app.repository.profiles.UserRepository;
import com.centros_sass.app.service.BathroomScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BathroomScheduleServiceImpl implements BathroomScheduleService {

    private final BathroomScheduleRepository bathroomScheduleRepository;
    private final UserRepository userRepository;
    private final BathroomTurnRepository bathroomTurnRepository;
    private final BathroomTaskRepository bathroomTaskRepository;
    private final BathroomScheduleMapper bathroomScheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BathroomScheduleResponseDTO> findAll(Pageable pageable) {
        return bathroomScheduleRepository.findAllByIsActiveTrue(pageable)
                .map(bathroomScheduleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BathroomScheduleResponseDTO> findAllInactive(Pageable pageable) {
        return bathroomScheduleRepository.findAllByIsActiveFalse(pageable)
                .map(bathroomScheduleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BathroomScheduleResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return bathroomScheduleRepository.findAll(pageable)
                .map(bathroomScheduleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BathroomScheduleResponseDTO> findById(Integer id) {
        return bathroomScheduleRepository.findById(id)
                .map(bathroomScheduleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BathroomScheduleResponseDTO> findByUserId(Integer userId, Pageable pageable) {
        return bathroomScheduleRepository.findAllByUserId(userId, pageable)
                .map(bathroomScheduleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BathroomScheduleResponseDTO> findByTurnId(Integer turnId, Pageable pageable) {
        return bathroomScheduleRepository.findAllByBathroomTurnId(turnId, pageable)
                .map(bathroomScheduleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BathroomScheduleResponseDTO> findByTaskId(Integer taskId, Pageable pageable) {
        return bathroomScheduleRepository.findAllByBathroomTaskId(taskId, pageable)
                .map(bathroomScheduleMapper::toResponse);
    }

    @Override
    @Transactional
    public BathroomScheduleResponseDTO save(BathroomScheduleRequestDTO dto) {
        BathroomSchedule schedule = bathroomScheduleMapper.toEntity(dto);
        assignRelations(schedule, dto.userId(), dto.bathroomTurnId(), dto.bathroomTaskId());
        BathroomSchedule saved = bathroomScheduleRepository.save(schedule);
        return bathroomScheduleMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<BathroomScheduleResponseDTO> update(Integer id, BathroomScheduleUpdateDTO dto) {
        return bathroomScheduleRepository.findById(id).map(existing -> {
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
                existing.setUser(user);
            }
            if (dto.getBathroomTurnId() != null) {
                BathroomTurn turn = bathroomTurnRepository.findById(dto.getBathroomTurnId())
                        .orElseThrow(() -> new ResourceNotFoundException("BathroomTurn", "id", dto.getBathroomTurnId()));
                existing.setBathroomTurn(turn);
            }
            if (dto.getBathroomTaskId() != null) {
                BathroomTask task = bathroomTaskRepository.findById(dto.getBathroomTaskId())
                        .orElseThrow(() -> new ResourceNotFoundException("BathroomTask", "id", dto.getBathroomTaskId()));
                existing.setBathroomTask(task);
            }

            bathroomScheduleMapper.updateFromDto(dto, existing);
            BathroomSchedule saved = bathroomScheduleRepository.saveAndFlush(existing);
            return bathroomScheduleMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<BathroomScheduleResponseDTO> delete(Integer id) {
        return bathroomScheduleRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            BathroomSchedule saved = bathroomScheduleRepository.saveAndFlush(existing);
            return bathroomScheduleMapper.toResponse(saved);
        });
    }

    // Métodos Helpers

    private void assignRelations(BathroomSchedule schedule, Integer userId, Integer turnId, Integer taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        schedule.setUser(user);

        BathroomTurn turn = bathroomTurnRepository.findById(turnId)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomTurn", "id", turnId));
        schedule.setBathroomTurn(turn);

        BathroomTask task = bathroomTaskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomTask", "id", taskId));
        schedule.setBathroomTask(task);
    }

}
