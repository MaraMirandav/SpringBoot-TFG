package com.centros_sass.app.repository.catalogs.belongings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.belongings.ItemCondition;

@Repository
public interface ItemConditionRepository extends JpaRepository<ItemCondition, Integer> {

    boolean existsByConditionName(String conditionName);

    boolean existsByConditionNameAndIdNot(String conditionName, Integer id);

}
