package com.centros_sass.app.repository.bathroom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.bathroom.BathroomSchedule;

@Repository
public interface BathroomScheduleRepository extends JpaRepository<BathroomSchedule, Integer> {

    Page<BathroomSchedule> findAllByIsActiveTrue(Pageable pageable);

    Page<BathroomSchedule> findAllByIsActiveFalse(Pageable pageable);

    Page<BathroomSchedule> findAllByUserId(Integer userId, Pageable pageable);

    Page<BathroomSchedule> findAllByBathroomTurnId(Integer bathroomTurnId, Pageable pageable);

    Page<BathroomSchedule> findAllByBathroomTaskId(Integer bathroomTaskId, Pageable pageable);

}
