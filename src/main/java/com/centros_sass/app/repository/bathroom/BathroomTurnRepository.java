package com.centros_sass.app.repository.bathroom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.bathroom.BathroomTurn;

@Repository
public interface BathroomTurnRepository extends JpaRepository<BathroomTurn, Integer> {

    Page<BathroomTurn> findAll(Pageable pageable);

    boolean existsByTurnName(String turnName);

    boolean existsByTurnNameAndIdNot(String turnName, Integer id);

}
