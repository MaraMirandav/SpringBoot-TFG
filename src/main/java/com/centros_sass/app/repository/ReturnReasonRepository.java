package com.centros_sass.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.dynamic.belongings.ReturnReason;

@Repository
public interface ReturnReasonRepository extends JpaRepository<ReturnReason, Integer> {

    Page<ReturnReason> findAllByIsActiveTrue(Pageable pageable);

    Page<ReturnReason> findAllByIsActiveFalse(Pageable pageable);

    boolean existsByReason(String reason);

    boolean existsByReasonAndIdNot(String reason, Integer id);

}
