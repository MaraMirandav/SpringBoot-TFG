package com.centros_sass.app.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.centros_sass.app.repository.WorkerRepository;
import com.centros_sass.app.repository.WorkerScheduleRecordRepository;
import com.centros_sass.app.repository.WorkerScheduleRepository;
import com.centros_sass.app.security.WorkerSecurity;
import com.centros_sass.app.service.WorkerScheduleRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerScheduleRecordServiceImpl implements WorkerScheduleRecordService {

    private final WorkerScheduleRecordRepository recordRepository;
    private final WorkerScheduleRepository scheduleRepository;
    private final WorkerScheduleRecordMapper recordMapper;
    private final WorkerRepository workerRepository;

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
    public WorkerScheduleRecordResponseDTO clockInToday() {
        Worker worker = getCurrentWorker();
        String todayDayName = getCurrentDayName();

        WorkerSchedule schedule = scheduleRepository
                .findByWorkerIdAndOpenDayDayNameAndIsActiveTrue(worker.getId(), todayDayName)
                .orElseThrow(() -> new BadRequestException("No tienes horario asignado para hoy"));

        if (recordRepository.existsByWorkerIdAndClockOutIsNullAndIsActiveTrue(worker.getId())) {
            throw new BadRequestException("Ya tienes un fichaje abierto. Debes fichar salida primero");
        }

        WorkerScheduleRecordRequestDTO dto = new WorkerScheduleRecordRequestDTO(
                schedule.getId(),
                LocalDateTime.now()
        );

        WorkerScheduleRecord record = recordMapper.toEntity(dto);
        record.setWorker(worker);
        record.setSchedule(schedule);

        WorkerScheduleRecord saved = recordRepository.saveAndFlush(record);
        return recordMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<WorkerScheduleRecordResponseDTO> clockOutToday() {
        Worker worker = getCurrentWorker();

        WorkerScheduleRecord activeRecord = recordRepository
                .findByWorkerIdAndClockOutIsNullAndIsActiveTrue(worker.getId())
                .orElseThrow(() -> new BadRequestException("No tienes ningún fichaje abierto"));

        activeRecord.setClockOut(LocalDateTime.now());

        WorkerScheduleRecord saved = recordRepository.saveAndFlush(activeRecord);
        return Optional.of(recordMapper.toResponse(saved));
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

    @Override
    @Transactional(readOnly = true)
    public List<WorkerScheduleRecordResponseDTO> findActiveClockIns(Integer workerId) {
        return recordRepository.findByWorkerIdAndClockOutIsNullAndIsActiveTrue(workerId)
                .map(record -> List.of(recordMapper.toResponse(record)))
                .orElse(List.of());
    }

    /**
     * Obtiene el worker autenticado directamente del SecurityContext.
     * El JwtAuthenticationFilter carga un WorkerSecurity como principal,
     * que contiene la entidad Worker ya resuelta (sin query extra).
     */
    private Worker getCurrentWorker() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof WorkerSecurity workerSecurity) {
            return workerSecurity.getWorker();
        }
        // Fallback: si no es WorkerSecurity, busca por email (ej: admin u otro tipo de auth)
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return workerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "email", email));
    }

    private String getCurrentDayName() {
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        return switch (day) {
            case MONDAY -> "Lunes";
            case TUESDAY -> "Martes";
            case WEDNESDAY -> "Miércoles";
            case THURSDAY -> "Jueves";
            case FRIDAY -> "Viernes";
            case SATURDAY -> "Sábado";
            case SUNDAY -> "Domingo";
        };
    }

}
