package com.centros_sass.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.belongings.UserClothing;

@Repository
public interface UserClothingRepository extends JpaRepository<UserClothing, Integer> {

    Page<UserClothing> findAllByIsActiveTrue(Pageable pageable);

    Page<UserClothing> findAllByIsActiveFalse(Pageable pageable);

    Page<UserClothing> findAllByClothingTypeId(Integer clothingTypeId, Pageable pageable);

}
