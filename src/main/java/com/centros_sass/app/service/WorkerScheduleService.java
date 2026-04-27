package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.workerschedule.WorkerScheduleRequestDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleResponseDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleUpdateDTO;

public interface WorkerScheduleService {

    Page<WorkerScheduleResponseDTO> findByWorkerId(Integer workerId, Pageable pageable);

    Page<WorkerScheduleResponseDTO> findAll(Pageable pageable);

    Page<WorkerScheduleResponseDTO> findAllInactive(Pageable pageable);

    Optional<WorkerScheduleResponseDTO> findById(Integer id);

    WorkerScheduleResponseDTO save(WorkerScheduleRequestDTO dto);

    Optional<WorkerScheduleResponseDTO> update(Integer id, WorkerScheduleUpdateDTO dto);

    Optional<WorkerScheduleResponseDTO> delete(Integer id);

}
