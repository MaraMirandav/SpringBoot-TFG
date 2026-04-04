package com.centros_sass.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.centros_sass.app.model.profiles.workers.WorkerScheduleRecord;

public interface WorkerScheduleRecordRepository extends JpaRepository<WorkerScheduleRecord, Integer> {

    //Por worker
    Page<WorkerScheduleRecord> findByWorkerIdAndIsActiveTrue(Integer workerId, Pageable pageable);

    //Todos activos
    Page<WorkerScheduleRecord> findAllByIsActiveTrue(Pageable pageable);

    //Por schedule
    Optional<WorkerScheduleRecord> findByScheduleIdAndIsActiveTrue(Integer scheduleId);

    //Fichaje abierto
    Optional<WorkerScheduleRecord> findByWorkerIdAndClockOutIsNullAndIsActiveTrue(Integer workerId);

    boolean existsByWorkerIdAndClockOutIsNullAndIsActiveTrue(Integer workerId);

}
