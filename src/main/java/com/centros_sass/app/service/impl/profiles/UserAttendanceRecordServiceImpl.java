package com.centros_sass.app.service.impl.profiles;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordRequestDTO;
import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordResponseDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.profiles.UserAttendanceRecordMapper;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.users.UserAttendanceDay;
import com.centros_sass.app.model.profiles.users.UserAttendanceRecord;
import com.centros_sass.app.repository.profiles.UserAttendanceDayRepository;
import com.centros_sass.app.repository.profiles.UserAttendanceRecordRepository;
import com.centros_sass.app.repository.profiles.UserRepository;
import com.centros_sass.app.service.UserAttendanceRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAttendanceRecordServiceImpl implements UserAttendanceRecordService {

    private final UserAttendanceRecordRepository recordRepository;
    private final UserRepository userRepository;
    private final UserAttendanceDayRepository attendanceDayRepository;
    private final UserAttendanceRecordMapper recordMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserAttendanceRecordResponseDTO> findAll(Pageable pageable) {
        return recordRepository.findAll(pageable)
                .map(recordMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAttendanceRecordResponseDTO> findByUserId(Integer userId, Pageable pageable) {
        return recordRepository.findByUserId(userId, pageable)
                .map(recordMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAttendanceRecordResponseDTO> findById(Integer id) {
        return recordRepository.findById(id)
                .map(recordMapper::toResponse);
    }

    @Override
    @Transactional
    public UserAttendanceRecordResponseDTO save(UserAttendanceRecordRequestDTO dto) {
        // Validar que el usuario existe
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.userId()));

        // Validar que el día de asistencia existe
        UserAttendanceDay attendanceDay = attendanceDayRepository.findById(dto.attendanceDayId())
                .orElseThrow(() -> new ResourceNotFoundException("UserAttendanceDay", "id", dto.attendanceDayId()));

        // Validar que el día de asistencia pertenece al usuario
        if (!attendanceDay.getUser().getId().equals(dto.userId())) {
            throw new BadRequestException("El día de asistencia no pertenece al usuario indicado");
        }

        // Validar que no exista registro duplicado
        if (recordRepository.existsByUserIdAndAttendanceDayId(dto.userId(), dto.attendanceDayId())) {
            throw new BadRequestException("Ya existe un registro de asistencia para este usuario en este día");
        }

        UserAttendanceRecord entity = recordMapper.toEntity(dto);
        entity.setUser(user);
        entity.setAttendanceDay(attendanceDay);

        return recordMapper.toResponse(recordRepository.saveAndFlush(entity));
    }

    @Override
    @Transactional
    public Optional<UserAttendanceRecordResponseDTO> delete(Integer id) {
        return recordRepository.findById(id)
                .map(existing -> {
                    recordRepository.delete(existing);
                    return recordMapper.toResponse(existing);
                });
    }

}
