package com.centros_sass.app.service.impl.treatments;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.treatments.UserIllnessRequestDTO;
import com.centros_sass.app.dto.treatments.UserIllnessResponseDTO;
import com.centros_sass.app.dto.treatments.UserIllnessUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.treatments.UserIllnessMapper;
import com.centros_sass.app.model.catalogs.treatments.Illness;
import com.centros_sass.app.model.treatments.UserIllness;
import com.centros_sass.app.model.treatments.UserMedicalInfo;
import com.centros_sass.app.repository.catalogs.treatments.IllnessRepository;
import com.centros_sass.app.repository.treatments.UserIllnessRepository;
import com.centros_sass.app.repository.treatments.UserMedicalInfoRepository;
import com.centros_sass.app.service.UserIllnessService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserIllnessServiceImpl implements UserIllnessService {

    private final UserIllnessRepository userIllnessRepository;
    private final UserMedicalInfoRepository userMedicalInfoRepository;
    private final IllnessRepository illnessRepository;
    private final UserIllnessMapper userIllnessMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserIllnessResponseDTO> findAll(Pageable pageable) {
        return userIllnessRepository.findAllByIsActiveTrue(pageable)
                .map(userIllnessMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIllnessResponseDTO> findAllInactive(Pageable pageable) {
        return userIllnessRepository.findAllByIsActiveFalse(pageable)
                .map(userIllnessMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIllnessResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return userIllnessRepository.findAll(pageable)
                .map(userIllnessMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserIllnessResponseDTO> findById(Integer id) {
        return userIllnessRepository.findById(id)
                .map(userIllnessMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIllnessResponseDTO> findByUserMedicalInfoId(Integer infoId, Pageable pageable) {
        return userIllnessRepository.findByUserMedicalInfoIdAndIsActiveTrue(infoId, pageable)
                .map(userIllnessMapper::toResponse);
    }

    @Override
    @Transactional
    public UserIllnessResponseDTO save(UserIllnessRequestDTO dto) {
        UserIllness illness = userIllnessMapper.toEntity(dto);
        assignRelations(illness, dto.userMedicalInfoId(), dto.illnessId());
        illness.setIsActive(true);

        UserIllness saved = userIllnessRepository.save(illness);
        return userIllnessMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserIllnessResponseDTO> update(Integer id, UserIllnessUpdateDTO dto) {
        return userIllnessRepository.findById(id).map(existing -> {
            if (dto.getUserMedicalInfoId() != null) {
                UserMedicalInfo info = userMedicalInfoRepository.findById(dto.getUserMedicalInfoId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserMedicalInfo", "id", dto.getUserMedicalInfoId()));
                existing.setUserMedicalInfo(info);
            }

            if (dto.getIllnessId() != null) {
                Illness illness = illnessRepository.findById(dto.getIllnessId())
                        .orElseThrow(() -> new ResourceNotFoundException("Illness", "id", dto.getIllnessId()));
                existing.setIllness(illness);
            }

            userIllnessMapper.updateFromDto(dto, existing);
            UserIllness saved = userIllnessRepository.saveAndFlush(existing);
            return userIllnessMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<UserIllnessResponseDTO> delete(Integer id) {
        return userIllnessRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            UserIllness saved = userIllnessRepository.saveAndFlush(existing);
            return userIllnessMapper.toResponse(saved);
        });
    }

    private void assignRelations(UserIllness illness, Integer infoId, Integer illnessId) {
        UserMedicalInfo info = userMedicalInfoRepository.findById(infoId)
                .orElseThrow(() -> new ResourceNotFoundException("UserMedicalInfo", "id", infoId));
        illness.setUserMedicalInfo(info);

        Illness i = illnessRepository.findById(illnessId)
                .orElseThrow(() -> new ResourceNotFoundException("Illness", "id", illnessId));
        illness.setIllness(i);
    }

}
