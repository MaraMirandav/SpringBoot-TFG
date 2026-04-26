package com.centros_sass.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.dynamic.belongings.DiaperType;

@Repository
public interface DiaperTypeRepository extends JpaRepository<DiaperType, Integer> {

    Page<DiaperType> findAllByIsActiveTrue(Pageable pageable);

    Page<DiaperType> findAllByIsActiveFalse(Pageable pageable);

    boolean existsByType(String type);

    boolean existsByTypeAndIdNot(String type, Integer id);

}
