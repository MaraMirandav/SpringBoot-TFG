package com.centros_sass.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.dynamic.belongings.ObjectType;

@Repository
public interface ObjectTypeRepository extends JpaRepository<ObjectType, Integer> {

    Page<ObjectType> findAllByIsActiveTrue(Pageable pageable);

    Page<ObjectType> findAllByIsActiveFalse(Pageable pageable);

    boolean existsByObjectName(String objectName);

    boolean existsByObjectNameAndIdNot(String objectName, Integer id);

}
