package com.centros_sass.app.repository.catalogs.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.transport.RouteShift;

@Repository
public interface RouteShiftRepository extends JpaRepository<RouteShift, Integer> {

    boolean existsByRouteName(String routeName);

    boolean existsByRouteNameAndIdNot(String routeName, Integer id);

}
