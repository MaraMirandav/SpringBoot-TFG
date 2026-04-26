package com.centros_sass.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.dynamic.belongings.ClothingType;

@Repository
public interface ClothingTypeRepository extends JpaRepository<ClothingType, Integer> {

    Page<ClothingType> findAllByIsActiveTrue(Pageable pageable);

    Page<ClothingType> findAllByIsActiveFalse(Pageable pageable);

    boolean existsByClothingName(String clothingName);

    boolean existsByClothingNameAndIdNot(String clothingName, Integer id);

}
