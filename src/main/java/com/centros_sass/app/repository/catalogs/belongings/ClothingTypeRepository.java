package com.centros_sass.app.repository.catalogs.belongings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.belongings.ClothingType;

@Repository
public interface ClothingTypeRepository extends JpaRepository<ClothingType, Integer> {

    boolean existsByClothingName(String clothingName);

    boolean existsByClothingNameAndIdNot(String clothingName, Integer id);

}
