package com.centros_sass.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centros_sass.app.model.catalogs.fixed.transport.RouteShift;

public interface RouteShiftRepository extends JpaRepository<RouteShift, Integer> {

    Optional<RouteShift> findByRouteName(String routeName);

    boolean existsByRouteName(String routeName);

}
