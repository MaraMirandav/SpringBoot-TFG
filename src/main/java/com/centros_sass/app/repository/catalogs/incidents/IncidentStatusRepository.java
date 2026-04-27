package com.centros_sass.app.repository.catalogs.incidents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.incidents.IncidentStatus;

@Repository
public interface IncidentStatusRepository extends JpaRepository<IncidentStatus, Integer> {

    boolean existsByStatusName(String statusName);

    boolean existsByStatusNameAndIdNot(String statusName, Integer id);

    java.util.Optional<IncidentStatus> findByStatusName(String statusName);

}
