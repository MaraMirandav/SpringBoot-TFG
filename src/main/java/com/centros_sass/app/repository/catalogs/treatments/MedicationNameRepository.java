package com.centros_sass.app.repository.catalogs.treatments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.treatments.MedicationName;

@Repository
public interface MedicationNameRepository extends JpaRepository<MedicationName, Integer> {

    boolean existsByMedicationName(String medicationName);

    boolean existsByMedicationNameAndIdNot(String medicationName, Integer id);

}
