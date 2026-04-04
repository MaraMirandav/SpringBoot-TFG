package com.centros_sass.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordRequestDTO;
import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordResponseDTO;
import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.WorkerScheduleRecordMapper;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.model.profiles.workers.WorkerSchedule;
import com.centros_sass.app.model.profiles.workers.WorkerScheduleRecord;
import com.centros_sass.app.repository.WorkerScheduleRecordRepository;
import com.centros_sass.app.repository.WorkerScheduleRepository;
import com.centros_sass.app.service.WorkerScheduleRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerScheduleRecordServiceImpl implements WorkerScheduleRecordService {

    private final WorkerScheduleRecordRepository recordRepository;
    private final WorkerScheduleRepository scheduleRepository;
    private final WorkerScheduleRecordMapper recordMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<WorkerScheduleRecordResponseDTO> findByWorkerId(Integer workerId, Pageable pageable) {
        return recordRepository.findByWorkerIdAndIsActiveTrue(workerId, pageable)
                .map(recordMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkerScheduleRecordResponseDTO> findAll(Pageable pageable) {
        return recordRepository.findAllByIsActiveTrue(pageable)
                .map(recordMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkerScheduleRecordResponseDTO> findById(Integer id) {
        return recordRepository.findById(id)
                .map(recordMapper::toResponse);
    }

    @Override
    @Transactional
    public WorkerScheduleRecordResponseDTO clockIn(WorkerScheduleRecordRequestDTO dto) {
        WorkerSchedule schedule = scheduleRepository.findById(dto.scheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("WorkerSchedule", "id", dto.scheduleId()));

        if (!schedule.getIsActive()) {
            throw new BadRequestException("El horario asociado no está activo");
        }

        Worker worker = schedule.getWorker();
        if (!worker.getIsActive()) {
            throw new BadRequestException("El trabajador asociado al horario no está activo");
        }

        if (recordRepository.existsByWorkerIdAndClockOutIsNullAndIsActiveTrue(worker.getId())) {
            throw new BadRequestException(
                    "Ya tienes un fichaje abierto. Debes fichar salida antes de entrar en otro turno");
        }

        WorkerScheduleRecord record = recordMapper.toEntity(dto);
        record.setWorker(worker);
        record.setSchedule(schedule);

        WorkerScheduleRecord saved = recordRepository.save(record);
        return recordMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<WorkerScheduleRecordResponseDTO> clockOut(Integer id, WorkerScheduleRecordUpdateDTO dto) {
        return recordRepository.findById(id).map(existing -> {
            if (existing.getClockOut() != null) {
                throw new BadRequestException("Este fichaje ya tiene hora de salida registrada");
            }

            if (dto.getClockOut() != null && dto.getClockOut().isBefore(existing.getClockIn())) {
                throw new BadRequestException(
                        "La hora de salida no puede ser anterior a la hora de entrada");
            }

            existing.setClockOut(dto.getClockOut());

            WorkerScheduleRecord saved = recordRepository.saveAndFlush(existing);
            return recordMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkerScheduleRecordResponseDTO> findActiveClockIns(Integer workerId) {
        return recordRepository.findByWorkerIdAndClockOutIsNullAndIsActiveTrue(workerId)
                .map(record -> List.of(recordMapper.toResponse(record)))
                .orElse(List.of());
    }

    @Override
    @Transactional
    public Optional<WorkerScheduleRecordResponseDTO> update(Integer id, WorkerScheduleRecordUpdateDTO dto) {
        return recordRepository.findById(id).map(existing -> {
            recordMapper.updateFromDto(dto, existing);
            WorkerScheduleRecord saved = recordRepository.saveAndFlush(existing);
            return recordMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<WorkerScheduleRecordResponseDTO> delete(Integer id) {
        return recordRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            WorkerScheduleRecord saved = recordRepository.save(existing);
            return recordMapper.toResponse(saved);
        });
    }

}
