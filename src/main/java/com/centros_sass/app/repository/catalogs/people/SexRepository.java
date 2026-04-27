package com.centros_sass.app.repository.catalogs.people;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.people.Sex;

@Repository
public interface SexRepository extends JpaRepository<Sex, Integer> {

    boolean existsBySex(String sex);

    boolean existsBySexAndIdNot(String sex, Integer id);

}
