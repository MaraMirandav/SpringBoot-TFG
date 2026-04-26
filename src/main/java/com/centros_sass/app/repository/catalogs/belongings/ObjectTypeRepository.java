package com.centros_sass.app.repository.catalogs.belongings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.belongings.ObjectType;

@Repository
public interface ObjectTypeRepository extends JpaRepository<ObjectType, Integer> {

    boolean existsByObjectName(String objectName);

    boolean existsByObjectNameAndIdNot(String objectName, Integer id);

}
