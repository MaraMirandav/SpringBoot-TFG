package com.centros_sass.app.repository.catalogs.treatments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.treatments.StorageCondition;

@Repository
public interface StorageConditionRepository extends JpaRepository<StorageCondition, Integer> {

    boolean existsByStorageName(String storageName);

    boolean existsByStorageNameAndIdNot(String storageName, Integer id);

}
