package com.centros_sass.app.repository.catalogs.incidents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.incidents.center.CdSignificanceType;

@Repository
public interface CdSignificanceTypeRepository extends JpaRepository<CdSignificanceType, Integer> {

    boolean existsBySignificanceName(String significanceName);

    boolean existsBySignificanceNameAndIdNot(String significanceName, Integer id);

}
