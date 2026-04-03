package com.centros_sass.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centros_sass.app.model.catalogs.fixed.calendar.OpenDay;

public interface OpenDayRepository extends JpaRepository<OpenDay, Integer> {

}
