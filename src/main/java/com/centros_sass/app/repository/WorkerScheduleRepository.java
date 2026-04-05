package com.centros_sass.app.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centros_sass.app.model.profiles.workers.WorkerSchedule;

public interface WorkerScheduleRepository extends JpaRepository<WorkerSchedule, Integer> {

    // Por worker
    Page<WorkerSchedule> findByWorkerIdAndIsActiveTrue(Integer workerId, Pageable pageable);

    // Todos activos
    Page<WorkerSchedule> findAllByIsActiveTrue(Pageable pageable);

    // Validación solapamiento de horarios
    @Query("SELECT ws FROM WorkerSchedule ws " +
            "WHERE ws.worker.id = :workerId " +
            "AND ws.openDay.id = :openDayId " +
            "AND ws.isActive = true " +
            "AND ws.id != :excludeId " +
            "AND ws.startAt < :endAt " +
            "AND ws.endAt > :startAt")
    List<WorkerSchedule> findOverlappingSchedules(
            @Param("workerId") Integer workerId,
            @Param("openDayId") Integer openDayId,
            @Param("startAt") LocalTime startAt,
            @Param("endAt") LocalTime endAt,
            @Param("excludeId") Integer excludeId);

    // Búsqueda por día de la semana (para clock-in/today)
    Optional<WorkerSchedule> findByWorkerIdAndOpenDayDayNameAndIsActiveTrue(Integer workerId, String dayName);

}
