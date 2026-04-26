package com.centros_sass.app.repository.catalogs.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.calendar.OpenDay;

@Repository
public interface OpenDayRepository extends JpaRepository<OpenDay, Integer> {

    boolean existsByDayName(String dayName);

    boolean existsByDayNameAndIdNot(String dayName, Integer id);

}
