package com.centros_sass.app.repository.transport;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.transport.TransportRouteUser;

@Repository
public interface TransportRouteUserRepository extends JpaRepository<TransportRouteUser, Integer> {

    Set<TransportRouteUser> findByRouteId(Integer routeId);

    Optional<TransportRouteUser> findByRouteIdAndUserId(Integer routeId, Integer userId);

    boolean existsByRouteIdAndUserId(Integer routeId, Integer userId);

    void deleteByRouteIdAndUserId(Integer routeId, Integer userId);
}
