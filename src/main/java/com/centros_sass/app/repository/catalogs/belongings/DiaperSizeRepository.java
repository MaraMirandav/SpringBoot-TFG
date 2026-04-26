package com.centros_sass.app.repository.catalogs.belongings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.belongings.DiaperSize;

@Repository
public interface DiaperSizeRepository extends JpaRepository<DiaperSize, Integer> {

    boolean existsBySize(String size);

    boolean existsBySizeAndIdNot(String size, Integer id);

}
