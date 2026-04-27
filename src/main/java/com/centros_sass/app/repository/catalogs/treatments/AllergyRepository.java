package com.centros_sass.app.repository.catalogs.treatments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.treatments.Allergy;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Integer> {

    boolean existsByAllergyName(String allergyName);

    boolean existsByAllergyNameAndIdNot(String allergyName, Integer id);

}
