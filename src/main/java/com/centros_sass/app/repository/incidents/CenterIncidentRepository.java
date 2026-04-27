package com.centros_sass.app.repository.incidents;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.incidents.center.CenterIncident;

@Repository
public interface CenterIncidentRepository extends JpaRepository<CenterIncident, Integer> {

    Page<CenterIncident> findAllByIsActiveTrue(Pageable pageable);

    Page<CenterIncident> findAllByIsActiveFalse(Pageable pageable);

    Page<CenterIncident> findByReportedByIdAndIsActiveTrue(Integer workerId, Pageable pageable);

    Page<CenterIncident> findByIncidentStatusIdAndIsActiveTrue(Integer statusId, Pageable pageable);

}
