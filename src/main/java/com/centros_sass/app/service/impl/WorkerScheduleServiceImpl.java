package com.centros_sass.app.service.impl;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.workerschedule.WorkerScheduleRequestDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleResponseDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.WorkerScheduleMapper;
import com.centros_sass.app.model.catalogs.fixed.calendar.OpenDay;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.model.profiles.workers.WorkerSchedule;
import com.centros_sass.app.repository.OpenDayRepository;
import com.centros_sass.app.repository.WorkerRepository;
import com.centros_sass.app.repository.WorkerScheduleRepository;
import com.centros_sass.app.service.WorkerScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerScheduleServiceImpl implements WorkerScheduleService {

    private final WorkerScheduleRepository workerScheduleRepository;
    private final WorkerRepository workerRepository;
    private final OpenDayRepository openDayRepository;
    private final WorkerScheduleMapper workerScheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<WorkerScheduleResponseDTO> findByWorkerId(Integer workerId, Pageable pageable) {
        return workerScheduleRepository.findByWorkerIdAndIsActiveTrue(workerId, pageable)
                .map(workerScheduleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkerScheduleResponseDTO> findAll(Pageable pageable) {
        return workerScheduleRepository.findAllByIsActiveTrue(pageable)
                .map(workerScheduleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkerScheduleResponseDTO> findById(Integer id) {
        return workerScheduleRepository.findById(id)
                .map(workerScheduleMapper::toResponse);
    }

    @Override
    @Transactional
    public WorkerScheduleResponseDTO save(WorkerScheduleRequestDTO dto) {
        if (dto.startAt().isAfter(dto.endAt()) || dto.startAt().equals(dto.endAt())) {
            throw new BadRequestException("La hora de entrada debe ser anterior a la hora de salida");
        }

        Worker worker = workerRepository.findById(dto.workerId())
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", dto.workerId()));

        OpenDay openDay = openDayRepository.findById(dto.openDayId())
                .orElseThrow(() -> new ResourceNotFoundException("OpenDay", "id", dto.openDayId()));

        if (!workerScheduleRepository.findOverlappingSchedules(
                dto.workerId(), dto.openDayId(), dto.startAt(), dto.endAt(), 0).isEmpty()) {
            throw new BadRequestException(
                    "El horario se solapa con otro horario existente para ese trabajador en el mismo día");
        }

        WorkerSchedule schedule = workerScheduleMapper.toEntity(dto);
        schedule.setWorker(worker);
        schedule.setOpenDay(openDay);

        WorkerSchedule saved = workerScheduleRepository.save(schedule);
        return workerScheduleMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<WorkerScheduleResponseDTO> update(Integer id, WorkerScheduleUpdateDTO dto) {
        return workerScheduleRepository.findById(id).map(existing -> {
            LocalTime effectiveStart = dto.getStartAt() != null ? dto.getStartAt() : existing.getStartAt();
            LocalTime effectiveEnd = dto.getEndAt() != null ? dto.getEndAt() : existing.getEndAt();

            if (effectiveStart.isAfter(effectiveEnd) || effectiveStart.equals(effectiveEnd)) {
                throw new BadRequestException("La hora de entrada debe ser anterior a la hora de salida");
            }

            if (dto.getOpenDayId() != null) {
                OpenDay openDay = openDayRepository.findById(dto.getOpenDayId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "OpenDay", "id", dto.getOpenDayId()));
                existing.setOpenDay(openDay);
            }

            if (!workerScheduleRepository.findOverlappingSchedules(
                    existing.getWorker().getId(),
                    dto.getOpenDayId() != null ? dto.getOpenDayId() : existing.getOpenDay().getId(),
                    effectiveStart,
                    effectiveEnd,
                    id).isEmpty()) {
                throw new BadRequestException(
                        "El horario se solapa con otro horario existente para ese trabajador en el mismo día");
            }

            workerScheduleMapper.updateFromDto(dto, existing);

            WorkerSchedule saved = workerScheduleRepository.saveAndFlush(existing);
            return workerScheduleMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<WorkerScheduleResponseDTO> delete(Integer id) {
        return workerScheduleRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            WorkerSchedule saved = workerScheduleRepository.saveAndFlush(existing);
            return workerScheduleMapper.toResponse(saved);
        });
    }

}
