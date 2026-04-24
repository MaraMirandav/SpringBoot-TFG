package com.centros_sass.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.dynamic.bathroom.BathroomTask;

@Repository
public interface BathroomTaskRepository extends JpaRepository<BathroomTask, Integer> {

    Page<BathroomTask> findAllByIsActiveTrue(Pageable pageable);

    Page<BathroomTask> findAllByIsActiveFalse(Pageable pageable);

    boolean existsByTaskName(String taskName);

    boolean existsByTaskNameAndIdNot(String taskName, Integer id);

}
