package com.centros_sass.app.service;

import java.util.List;
import java.util.Optional;

import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayRequestDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayResponseDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayUpdateDTO;

public interface UserAttendanceDayService {

    List<UserAttendanceDayResponseDTO> findAll();

    List<UserAttendanceDayResponseDTO> findAllIncludingInactive();

    Optional<UserAttendanceDayResponseDTO> findById(Integer id);

    List<UserAttendanceDayResponseDTO> findByUserId(Integer userId);

    UserAttendanceDayResponseDTO save(UserAttendanceDayRequestDTO dto);

    Optional<UserAttendanceDayResponseDTO> update(Integer id, UserAttendanceDayUpdateDTO dto);

    Optional<UserAttendanceDayResponseDTO> delete(Integer id);

}
