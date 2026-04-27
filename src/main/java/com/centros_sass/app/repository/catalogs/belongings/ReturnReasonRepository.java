package com.centros_sass.app.repository.catalogs.belongings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.belongings.ReturnReason;

@Repository
public interface ReturnReasonRepository extends JpaRepository<ReturnReason, Integer> {

    boolean existsByReason(String reason);

    boolean existsByReasonAndIdNot(String reason, Integer id);

}
