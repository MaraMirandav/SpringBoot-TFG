package com.centros_sass.app.service.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.belongings.UserBelongingRequestDTO;
import com.centros_sass.app.dto.belongings.UserBelongingResponseDTO;
import com.centros_sass.app.dto.belongings.UserBelongingUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.UserBelongingMapper;
import com.centros_sass.app.model.belongings.UserBelonging;
import com.centros_sass.app.model.belongings.UserClothing;
import com.centros_sass.app.model.belongings.UserDiaper;
import com.centros_sass.app.model.belongings.UserObject;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.UserBelongingRepository;
import com.centros_sass.app.repository.UserClothingRepository;
import com.centros_sass.app.repository.UserDiaperRepository;
import com.centros_sass.app.repository.UserObjectRepository;
import com.centros_sass.app.repository.UserRepository;
import com.centros_sass.app.repository.WorkerRepository;
import com.centros_sass.app.service.UserBelongingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBelongingServiceImpl implements UserBelongingService {

    private final UserBelongingRepository userBelongingRepository;
    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final UserClothingRepository userClothingRepository;
    private final UserDiaperRepository userDiaperRepository;
    private final UserObjectRepository userObjectRepository;
    private final UserBelongingMapper userBelongingMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserBelongingResponseDTO> findAll(Pageable pageable) {
        return userBelongingRepository.findAllByIsActiveTrue(pageable)
                .map(userBelongingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserBelongingResponseDTO> findAllInactive(Pageable pageable) {
        return userBelongingRepository.findAllByIsActiveFalse(pageable)
                .map(userBelongingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserBelongingResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return userBelongingRepository.findAll(pageable)
                .map(userBelongingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserBelongingResponseDTO> findById(Integer id) {
        return userBelongingRepository.findById(id)
                .map(userBelongingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserBelongingResponseDTO> findByUserId(Integer userId, Pageable pageable) {
        return userBelongingRepository.findAllByUserId(userId, pageable)
                .map(userBelongingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserBelongingResponseDTO> findByWorkerId(Integer workerId, Pageable pageable) {
        return userBelongingRepository.findAllByWorkerId(workerId, pageable)
                .map(userBelongingMapper::toResponse);
    }

    @Override
    @Transactional
    public UserBelongingResponseDTO save(UserBelongingRequestDTO dto) {
        validateSingleBelonging(dto.userClothingId(), dto.userDiaperId(), dto.userObjectId());

        UserBelonging belonging = userBelongingMapper.toEntity(dto);
        assignRelations(belonging, dto.userId(), dto.workerId(), dto.userClothingId(), dto.userDiaperId(), dto.userObjectId());
        UserBelonging saved = userBelongingRepository.save(belonging);
        return userBelongingMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserBelongingResponseDTO> update(Integer id, UserBelongingUpdateDTO dto) {
        return userBelongingRepository.findById(id).map(existing -> {
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
                existing.setUser(user);
            }
            if (dto.getWorkerId() != null) {
                Worker worker = workerRepository.findById(dto.getWorkerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", dto.getWorkerId()));
                existing.setWorker(worker);
            }

            // Validar y asignar pertenencia si viene algún ID
            if (dto.getUserClothingId() != null || dto.getUserDiaperId() != null || dto.getUserObjectId() != null) {
                validateSingleBelonging(dto.getUserClothingId(), dto.getUserDiaperId(), dto.getUserObjectId());
                assignBelongingRelations(existing, dto.getUserClothingId(), dto.getUserDiaperId(), dto.getUserObjectId());
            }

            userBelongingMapper.updateFromDto(dto, existing);
            UserBelonging saved = userBelongingRepository.saveAndFlush(existing);
            return userBelongingMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<UserBelongingResponseDTO> delete(Integer id) {
        return userBelongingRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            UserBelonging saved = userBelongingRepository.saveAndFlush(existing);
            return userBelongingMapper.toResponse(saved);
        });
    }

    // Métodos Helpers

    private void validateSingleBelonging(Integer clothingId, Integer diaperId, Integer objectId) {
        long present = Stream.of(clothingId, diaperId, objectId)
                .filter(Objects::nonNull)
                .count();
        if (present != 1) {
            throw new BadRequestException("Debe indicar exactamente una pertenencia: ropa (userClothingId), pañal (userDiaperId) u objeto (userObjectId)");
        }
    }

    private void assignRelations(UserBelonging belonging, Integer userId, Integer workerId,
                                 Integer clothingId, Integer diaperId, Integer objectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        belonging.setUser(user);

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", workerId));
        belonging.setWorker(worker);

        assignBelongingRelations(belonging, clothingId, diaperId, objectId);
    }

    private void assignBelongingRelations(UserBelonging belonging, Integer clothingId, Integer diaperId, Integer objectId) {
        if (clothingId != null) {
            UserClothing clothing = userClothingRepository.findById(clothingId)
                    .orElseThrow(() -> new ResourceNotFoundException("UserClothing", "id", clothingId));
            belonging.setUserClothing(clothing);
            belonging.setUserDiaper(null);
            belonging.setUserObject(null);
        } else if (diaperId != null) {
            UserDiaper diaper = userDiaperRepository.findById(diaperId)
                    .orElseThrow(() -> new ResourceNotFoundException("UserDiaper", "id", diaperId));
            belonging.setUserClothing(null);
            belonging.setUserDiaper(diaper);
            belonging.setUserObject(null);
        } else if (objectId != null) {
            UserObject obj = userObjectRepository.findById(objectId)
                    .orElseThrow(() -> new ResourceNotFoundException("UserObject", "id", objectId));
            belonging.setUserClothing(null);
            belonging.setUserDiaper(null);
            belonging.setUserObject(obj);
        }
    }

}
