package com.centros_sass.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayRequestDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayResponseDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.UserAttendanceDayMapper;
import com.centros_sass.app.model.catalogs.fixed.calendar.OpenDay;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.users.UserAttendanceDay;
import com.centros_sass.app.repository.OpenDayRepository;
import com.centros_sass.app.repository.UserAttendanceDayRepository;
import com.centros_sass.app.repository.UserRepository;
import com.centros_sass.app.service.UserAttendanceDayService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAttendanceDayServiceImpl implements UserAttendanceDayService {

    private final UserAttendanceDayRepository attendanceDayRepository;
    private final UserRepository userRepository;
    private final OpenDayRepository openDayRepository;
    private final UserAttendanceDayMapper attendanceDayMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserAttendanceDayResponseDTO> findAll() {
        return attendanceDayRepository.findByIsActiveTrue(null)
                .stream()
                .map(attendanceDayMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAttendanceDayResponseDTO> findAllInactive() {
        return attendanceDayRepository.findByIsActiveFalse(null)
                .stream()
                .map(attendanceDayMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAttendanceDayResponseDTO> findAllIncludingInactive() {
        return attendanceDayRepository.findAll()
                .stream()
                .map(attendanceDayMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAttendanceDayResponseDTO> findById(Integer id) {
        return attendanceDayRepository.findById(id)
                .map(attendanceDayMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAttendanceDayResponseDTO> findByUserId(Integer userId) {
        return attendanceDayRepository.findByUserIdAndIsActiveTrue(userId, null)
                .stream()
                .map(attendanceDayMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserAttendanceDayResponseDTO save(UserAttendanceDayRequestDTO dto) {
        // Validar que el usuario existe
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.userId()));

        // Validar que el día existe
        OpenDay day = openDayRepository.findById(dto.dayId())
                .orElseThrow(() -> new ResourceNotFoundException("OpenDay", "id", dto.dayId()));

        // Validar que no exista duplicado
        if (attendanceDayRepository.existsByUserIdAndDayIdAndIsActiveTrue(dto.userId(), dto.dayId())) {
            throw new BadRequestException("Ya existe una planilla de asistencia para este usuario en este día");
        }

        UserAttendanceDay entity = attendanceDayMapper.toEntity(dto);
        entity.setUser(user);
        entity.setDay(day);

        return attendanceDayMapper.toResponse(attendanceDayRepository.save(entity));
    }

    @Override
    @Transactional
    public Optional<UserAttendanceDayResponseDTO> update(Integer id, UserAttendanceDayUpdateDTO dto) {
        return attendanceDayRepository.findById(id)
                .map(existing -> {
                    // Si se actualiza el día, validar que existe
                    if (dto.getDayId() != null) {
                        OpenDay day = openDayRepository.findById(dto.getDayId())
                                .orElseThrow(() -> new ResourceNotFoundException("OpenDay", "id", dto.getDayId()));

                        // Validar que no exista duplicado con el nuevo día
                        if (!existing.getDay().getId().equals(dto.getDayId())
                                && attendanceDayRepository.existsByUserIdAndDayIdAndIsActiveTrue(
                                        existing.getUser().getId(), dto.getDayId())) {
                            throw new BadRequestException("Ya existe una planilla de asistencia para este usuario en este día");
                        }

                        existing.setDay(day);
                    }

                    attendanceDayMapper.updateFromDto(dto, existing);
                    UserAttendanceDay saved = attendanceDayRepository.saveAndFlush(existing);
                    return attendanceDayMapper.toResponse(saved);
                });
    }

    @Override
    @Transactional
    public Optional<UserAttendanceDayResponseDTO> delete(Integer id) {
        return attendanceDayRepository.findById(id)
                .map(existing -> {
                    existing.setIsActive(false);
                    UserAttendanceDay saved = attendanceDayRepository.save(existing);
                    return attendanceDayMapper.toResponse(saved);
                });
    }

}
