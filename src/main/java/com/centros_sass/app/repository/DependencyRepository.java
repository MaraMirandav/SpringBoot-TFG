package com.centros_sass.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centros_sass.app.model.catalogs.fixed.people.Dependency;

public interface DependencyRepository extends JpaRepository<Dependency, Integer> {
}
