package com.centros_sass.app.service.impl.treatments;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.treatments.UserMedicalInfoRequestDTO;
import com.centros_sass.app.dto.treatments.UserMedicalInfoResponseDTO;
import com.centros_sass.app.dto.treatments.UserMedicalInfoUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.treatments.UserMedicalInfoMapper;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.model.treatments.UserMedicalInfo;
import com.centros_sass.app.repository.profiles.UserRepository;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.app.repository.treatments.UserMedicalInfoRepository;
import com.centros_sass.app.service.UserMedicalInfoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMedicalInfoServiceImpl implements UserMedicalInfoService {

    private final UserMedicalInfoRepository userMedicalInfoRepository;
    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final UserMedicalInfoMapper userMedicalInfoMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserMedicalInfoResponseDTO> findAll(Pageable pageable) {
        return userMedicalInfoRepository.findAllByIsActiveTrue(pageable)
                .map(userMedicalInfoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserMedicalInfoResponseDTO> findAllInactive(Pageable pageable) {
        return userMedicalInfoRepository.findAllByIsActiveFalse(pageable)
                .map(userMedicalInfoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserMedicalInfoResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return userMedicalInfoRepository.findAll(pageable)
                .map(userMedicalInfoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserMedicalInfoResponseDTO> findById(Integer id) {
        return userMedicalInfoRepository.findById(id)
                .map(userMedicalInfoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserMedicalInfoResponseDTO> findByUserId(Integer userId) {
        return userMedicalInfoRepository.findByUserIdAndIsActiveTrue(userId)
                .map(userMedicalInfoMapper::toResponse);
    }

    @Override
    @Transactional
    public UserMedicalInfoResponseDTO save(UserMedicalInfoRequestDTO dto) {
        if (userMedicalInfoRepository.existsByUserIdAndIsActiveTrue(dto.userId())) {
            throw new BadRequestException("El usuario ya tiene una ficha médica activa");
        }

        UserMedicalInfo info = userMedicalInfoMapper.toEntity(dto);
        assignRelations(info, dto.userId(), dto.workerId());
        info.setIsActive(true);

        UserMedicalInfo saved = userMedicalInfoRepository.save(info);
        return userMedicalInfoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserMedicalInfoResponseDTO> update(Integer id, UserMedicalInfoUpdateDTO dto) {
        return userMedicalInfoRepository.findById(id).map(existing -> {
            if (dto.getUserId() != null && !dto.getUserId().equals(existing.getUser().getId())) {
                if (userMedicalInfoRepository.existsByUserIdAndIsActiveTrue(dto.getUserId())) {
                    throw new BadRequestException("El usuario destino ya tiene una ficha médica activa");
                }
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
                existing.setUser(user);
            }

            if (dto.getWorkerId() != null) {
                Worker worker = workerRepository.findById(dto.getWorkerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", dto.getWorkerId()));
                existing.setWorker(worker);
            }

            userMedicalInfoMapper.updateFromDto(dto, existing);
            UserMedicalInfo saved = userMedicalInfoRepository.saveAndFlush(existing);
            return userMedicalInfoMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<UserMedicalInfoResponseDTO> delete(Integer id) {
        return userMedicalInfoRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            UserMedicalInfo saved = userMedicalInfoRepository.saveAndFlush(existing);
            return userMedicalInfoMapper.toResponse(saved);
        });
    }

    private void assignRelations(UserMedicalInfo info, Integer userId, Integer workerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        info.setUser(user);

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", workerId));
        info.setWorker(worker);
    }

}
