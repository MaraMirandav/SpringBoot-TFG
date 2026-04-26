package com.centros_sass.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.dynamic.belongings.DiaperSize;

@Repository
public interface DiaperSizeRepository extends JpaRepository<DiaperSize, Integer> {

    Page<DiaperSize> findAllByIsActiveTrue(Pageable pageable);

    Page<DiaperSize> findAllByIsActiveFalse(Pageable pageable);

    boolean existsBySize(String size);

    boolean existsBySizeAndIdNot(String size, Integer id);

}
