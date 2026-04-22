package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordRequestDTO;
import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordResponseDTO;

public interface UserAttendanceRecordService {

    Page<UserAttendanceRecordResponseDTO> findAll(Pageable pageable);

    Page<UserAttendanceRecordResponseDTO> findByUserId(Integer userId, Pageable pageable);

    Optional<UserAttendanceRecordResponseDTO> findById(Integer id);

    UserAttendanceRecordResponseDTO save(UserAttendanceRecordRequestDTO dto);

    Optional<UserAttendanceRecordResponseDTO> delete(Integer id);

}
