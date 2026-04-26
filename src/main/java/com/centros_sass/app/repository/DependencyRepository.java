package com.centros_sass.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.dynamic.people.Dependency;

@Repository
public interface DependencyRepository extends JpaRepository<Dependency, Integer> {

    boolean existsByDependencyLevel(String dependencyLevel);

    boolean existsByDependencyLevelAndIdNot(String dependencyLevel, Integer id);

}
