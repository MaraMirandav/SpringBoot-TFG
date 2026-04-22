package com.centros_sass.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.profiles.users.UserAttendanceDay;

@Repository
public interface UserAttendanceDayRepository extends JpaRepository<UserAttendanceDay, Integer> {

    Page<UserAttendanceDay> findByUserIdAndIsActiveTrue(Integer userId, Pageable pageable);

    Page<UserAttendanceDay> findByIsActiveTrue(Pageable pageable);

    Page<UserAttendanceDay> findByIsActiveFalse(Pageable pageable);

    Optional<UserAttendanceDay> findByUserIdAndDayIdAndIsActiveTrue(Integer userId, Integer dayId);

    boolean existsByUserIdAndDayIdAndIsActiveTrue(Integer userId, Integer dayId);

}
