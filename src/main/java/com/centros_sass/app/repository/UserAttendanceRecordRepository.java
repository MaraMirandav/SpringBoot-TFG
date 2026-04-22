package com.centros_sass.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.profiles.users.UserAttendanceRecord;

@Repository
public interface UserAttendanceRecordRepository extends JpaRepository<UserAttendanceRecord, Integer> {

    Page<UserAttendanceRecord> findByUserId(Integer userId, Pageable pageable);

    boolean existsByUserIdAndAttendanceDayId(Integer userId, Integer attendanceDayId);

}
