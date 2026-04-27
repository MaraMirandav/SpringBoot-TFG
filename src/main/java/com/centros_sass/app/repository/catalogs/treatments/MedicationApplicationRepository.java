package com.centros_sass.app.repository.catalogs.treatments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.treatments.MedicationApplication;

@Repository
public interface MedicationApplicationRepository extends JpaRepository<MedicationApplication, Integer> {

    boolean existsByMedicationApplicationName(String medicationApplicationName);

    boolean existsByMedicationApplicationNameAndIdNot(String medicationApplicationName, Integer id);

}
