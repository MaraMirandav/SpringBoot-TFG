package com.centros_sass.app.repository.catalogs.belongings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.belongings.DiaperType;

@Repository
public interface DiaperTypeRepository extends JpaRepository<DiaperType, Integer> {

    boolean existsByType(String type);

    boolean existsByTypeAndIdNot(String type, Integer id);

}
