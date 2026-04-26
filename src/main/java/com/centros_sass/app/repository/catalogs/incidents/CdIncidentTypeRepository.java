package com.centros_sass.app.repository.catalogs.incidents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.incidents.center.CdIncidentType;

@Repository
public interface CdIncidentTypeRepository extends JpaRepository<CdIncidentType, Integer> {

    boolean existsByIncidentName(String incidentName);

    boolean existsByIncidentNameAndIdNot(String incidentName, Integer id);

}
