package com.centros_sass.app.repository.catalogs.treatments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.treatments.Illness;

@Repository
public interface IllnessRepository extends JpaRepository<Illness, Integer> {

    boolean existsByIllnessName(String illnessName);

    boolean existsByIllnessNameAndIdNot(String illnessName, Integer id);

}
