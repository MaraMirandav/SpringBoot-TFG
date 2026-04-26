package com.centros_sass.app.repository.catalogs.bathroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.bathroom.BathroomTask;

@Repository
public interface BathroomTaskRepository extends JpaRepository<BathroomTask, Integer> {

    boolean existsByTaskName(String taskName);

    boolean existsByTaskNameAndIdNot(String taskName, Integer id);

}
