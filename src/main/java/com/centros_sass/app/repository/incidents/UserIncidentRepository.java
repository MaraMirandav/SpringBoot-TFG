package com.centros_sass.app.repository.incidents;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.incidents.user.UserIncident;

@Repository
public interface UserIncidentRepository extends JpaRepository<UserIncident, Integer> {

    Page<UserIncident> findAllByIsActiveTrue(Pageable pageable);

    Page<UserIncident> findAllByIsActiveFalse(Pageable pageable);

    Page<UserIncident> findByUserIdAndIsActiveTrue(Integer userId, Pageable pageable);

    Page<UserIncident> findByReportedByIdAndIsActiveTrue(Integer workerId, Pageable pageable);

    Page<UserIncident> findByIncidentStatusIdAndIsActiveTrue(Integer statusId, Pageable pageable);

}
