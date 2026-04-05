package com.centros_sass.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordResponseDTO;
import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordUpdateDTO;

public interface WorkerScheduleRecordService {

    Page<WorkerScheduleRecordResponseDTO> findByWorkerId(Integer workerId, Pageable pageable);

    Page<WorkerScheduleRecordResponseDTO> findAll(Pageable pageable);

    Optional<WorkerScheduleRecordResponseDTO> findById(Integer id);

    WorkerScheduleRecordResponseDTO clockInToday();

    Optional<WorkerScheduleRecordResponseDTO> clockOutToday();

    Optional<WorkerScheduleRecordResponseDTO> update(Integer id, WorkerScheduleRecordUpdateDTO dto);

    Optional<WorkerScheduleRecordResponseDTO> delete(Integer id);

    List<WorkerScheduleRecordResponseDTO> findActiveClockIns(Integer workerId);

}
