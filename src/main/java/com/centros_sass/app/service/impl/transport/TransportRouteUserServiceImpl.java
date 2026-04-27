package com.centros_sass.app.service.impl.transport;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.transport.TransportRouteUserRequestDTO;
import com.centros_sass.app.dto.transport.TransportRouteUserResponseDTO;
import com.centros_sass.app.dto.transport.TransportRouteUserUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.transport.TransportRouteUserMapper;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.transport.TransportRoute;
import com.centros_sass.app.model.transport.TransportRouteUser;
import com.centros_sass.app.repository.profiles.UserRepository;
import com.centros_sass.app.repository.transport.TransportRouteRepository;
import com.centros_sass.app.repository.transport.TransportRouteUserRepository;
import com.centros_sass.app.service.TransportRouteUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransportRouteUserServiceImpl implements TransportRouteUserService {

    private final TransportRouteUserRepository transportRouteUserRepository;
    private final TransportRouteRepository transportRouteRepository;
    private final UserRepository userRepository;
    private final TransportRouteUserMapper transportRouteUserMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TransportRouteUserResponseDTO> findAllByRoute(Integer routeId) {
        return transportRouteUserRepository.findByRouteId(routeId).stream()
                .map(transportRouteUserMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransportRouteUserResponseDTO> findByRouteIdAndUserId(Integer routeId, Integer userId) {
        return transportRouteUserRepository.findByRouteIdAndUserId(routeId, userId)
                .map(transportRouteUserMapper::toResponse);
    }

    @Override
    @Transactional
    public TransportRouteUserResponseDTO save(Integer routeId, TransportRouteUserRequestDTO dto) {
        TransportRoute route = transportRouteRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("TransportRoute", "id", routeId));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.userId()));

        if (transportRouteUserRepository.existsByRouteIdAndUserId(routeId, dto.userId())) {
            throw new BadRequestException("El usuario ya está asignado a esta ruta");
        }

        TransportRouteUser entity = transportRouteUserMapper.toEntity(dto);
        entity.setRoute(route);
        entity.setUser(user);

        if (entity.getUsesWheelchair() == null) {
            entity.setUsesWheelchair(false);
        }

        TransportRouteUser saved = transportRouteUserRepository.save(entity);
        return transportRouteUserMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<TransportRouteUserResponseDTO> update(Integer routeId, Integer userId, TransportRouteUserUpdateDTO dto) {
        return transportRouteUserRepository.findByRouteIdAndUserId(routeId, userId).map(existing -> {
            transportRouteUserMapper.updateFromDto(dto, existing);
            TransportRouteUser saved = transportRouteUserRepository.saveAndFlush(existing);
            return transportRouteUserMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer routeId, Integer userId) {
        Optional<TransportRouteUser> entity = transportRouteUserRepository.findByRouteIdAndUserId(routeId, userId);
        if (entity.isEmpty()) {
            throw new ResourceNotFoundException("TransportRouteUser", "routeId/userId", routeId + "/" + userId);
        }
        transportRouteUserRepository.delete(entity.get());
    }

}
