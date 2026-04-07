package com.centros_sass.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centros_sass.app.model.catalogs.fixed.people.Sex;

public interface SexRepository extends JpaRepository<Sex, Integer> {
}
