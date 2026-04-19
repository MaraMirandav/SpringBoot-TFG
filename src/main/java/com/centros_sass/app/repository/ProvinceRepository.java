package com.centros_sass.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.fixed.address.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
}