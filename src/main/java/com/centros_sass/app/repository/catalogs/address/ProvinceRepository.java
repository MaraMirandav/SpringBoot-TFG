package com.centros_sass.app.repository.catalogs.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.address.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    boolean existsByProvinceName(String provinceName);

    boolean existsByProvinceNameAndIdNot(String provinceName, Integer id);

}
