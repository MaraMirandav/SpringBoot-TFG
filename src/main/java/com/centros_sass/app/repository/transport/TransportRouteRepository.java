package com.centros_sass.app.repository.transport;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.centros_sass.app.model.transport.TransportRoute;

public interface TransportRouteRepository extends JpaRepository<TransportRoute, Integer> {

    Page<TransportRoute> findAllByIsActiveTrue(Pageable pageable);

    Page<TransportRoute> findAllByIsActiveFalse(Pageable pageable);

    Page<TransportRoute> findByRouteShiftIdAndIsActiveTrue(Integer routeShiftId, Pageable pageable);

    List<TransportRoute> findByDriverId(Integer driverId);

    List<TransportRoute> findByCopilotId(Integer copilotId);

    boolean existsByRouteNumber(Integer routeNumber);

    boolean existsByRouteNumberAndIdNot(Integer routeNumber, Integer id);

}
