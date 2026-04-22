package com.centros_sass.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.centros_sass.app.model.transport.RouteVehicle;

public interface RouteVehicleRepository extends JpaRepository<RouteVehicle, Integer> {

    Optional<RouteVehicle> findByLicensePlate(String licensePlate);

    boolean existsByLicensePlate(String licensePlate);

    Page<RouteVehicle> findAllByIsActiveTrue(Pageable pageable);

    Page<RouteVehicle> findAllByIsActiveFalse(Pageable pageable);

}
