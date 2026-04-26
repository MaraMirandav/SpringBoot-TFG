package com.centros_sass.app.repository.catalogs.incidents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.incidents.user.UserIncidentType;

@Repository
public interface UserIncidentTypeRepository extends JpaRepository<UserIncidentType, Integer> {

    boolean existsByIncidentName(String incidentName);

    boolean existsByIncidentNameAndIdNot(String incidentName, Integer id);

}
