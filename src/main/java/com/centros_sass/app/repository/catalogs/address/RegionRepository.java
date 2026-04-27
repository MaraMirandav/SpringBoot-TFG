package com.centros_sass.app.repository.catalogs.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.address.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    boolean existsByRegionName(String regionName);

    boolean existsByRegionNameAndIdNot(String regionName, Integer id);

}
