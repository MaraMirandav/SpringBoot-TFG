package com.centros_sass.app.service.impl;

import com.centros_sass.app.dto.usercontact.UserContactRequestDTO;
import com.centros_sass.app.dto.usercontact.UserContactResponseDTO;
import com.centros_sass.app.dto.usercontact.UserContactUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.UserContactMapper;
import com.centros_sass.app.model.catalogs.fixed.people.Relationship;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.users.UserContact;
import com.centros_sass.app.repository.RelationshipRepository;
import com.centros_sass.app.repository.UserContactRepository;
import com.centros_sass.app.repository.UserRepository;
import com.centros_sass.app.service.UserContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserContactServiceImpl implements UserContactService {

    private final UserContactRepository userContactRepository;
    private final UserRepository userRepository;
    private final RelationshipRepository relationshipRepository;
    private final UserContactMapper userContactMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserContactResponseDTO> findAll() {
        return userContactRepository.findAll()
                .stream()
                .filter(c -> c.getIsActive() != null && c.getIsActive())
                .map(userContactMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserContactResponseDTO> findAllIncludingInactive() {
        return userContactRepository.findAll(Pageable.unpaged())
                .stream()
                .map(userContactMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserContactResponseDTO> findById(Integer id) {
        return userContactRepository.findById(id)
                .map(userContactMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserContactResponseDTO> findByUserId(Integer userId) {
        return userContactRepository.findByUserIdAndIsActiveTrue(userId)
                .stream()
                .map(userContactMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserContactResponseDTO> findMainContactByUserId(Integer userId) {
        List<UserContact> contacts = userContactRepository.findByUserIdAndIsActiveTrueAndIsContactMainTrue(userId);
        if (contacts.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userContactMapper.toResponse(contacts.get(0)));
    }

    @Override
    @Transactional
    public UserContactResponseDTO save(UserContactRequestDTO dto) {
        Integer userId = dto.userId();

        if (dto.isContactMain() != null && dto.isContactMain()) {
            List<UserContact> existingMainContacts = userContactRepository.findByUserIdAndIsActiveTrueAndIsContactMainTrue(userId);
            for (UserContact existing : existingMainContacts) {
                existing.setIsContactMain(false);
                userContactRepository.saveAndFlush(existing);
            }
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Relationship relationship = relationshipRepository.findById(dto.contactRelationshipId())
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", dto.contactRelationshipId()));

        UserContact entity = userContactMapper.toEntity(dto);
        entity.setUser(user);
        entity.setContactRelationship(relationship);

        if (entity.getIsContactMain() == null) {
            entity.setIsContactMain(false);
        }

        return userContactMapper.toResponse(userContactRepository.save(entity));
    }

    @Override
    @Transactional
    public Optional<UserContactResponseDTO> update(Integer id, UserContactUpdateDTO dto) {
        return userContactRepository.findById(id)
                .map(existing -> {
                    if (dto.getContactRelationshipId() != null) {
                        Relationship relationship = relationshipRepository.findById(dto.getContactRelationshipId())
                                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", dto.getContactRelationshipId()));
                        existing.setContactRelationship(relationship);
                    }

                    if (dto.getIsContactMain() != null && dto.getIsContactMain()) {
                        List<UserContact> existingMainContacts = userContactRepository
                                .findByUserIdAndIsActiveTrueAndIsContactMainTrue(existing.getUser().getId());
                        for (UserContact mainContact : existingMainContacts) {
                            if (!mainContact.getId().equals(id)) {
                                mainContact.setIsContactMain(false);
                                userContactRepository.saveAndFlush(mainContact);
                            }
                        }
                    }

                    userContactMapper.updateFromDto(dto, existing);
                    UserContact saved = userContactRepository.save(existing);
                    return userContactMapper.toResponse(saved);
                });
    }

    @Override
    @Transactional
    public Optional<UserContactResponseDTO> delete(Integer id) {
        return userContactRepository.findById(id)
                .map(existing -> {
                    existing.setIsActive(false);
                    UserContact saved = userContactRepository.save(existing);
                    return userContactMapper.toResponse(saved);
                });
    }
}