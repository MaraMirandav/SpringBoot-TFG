package com.centros_sass.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.dynamic.belongings.ItemCondition;

@Repository
public interface ItemConditionRepository extends JpaRepository<ItemCondition, Integer> {

    Page<ItemCondition> findAllByIsActiveTrue(Pageable pageable);

    Page<ItemCondition> findAllByIsActiveFalse(Pageable pageable);

    boolean existsByConditionName(String conditionName);

    boolean existsByConditionNameAndIdNot(String conditionName, Integer id);

}
