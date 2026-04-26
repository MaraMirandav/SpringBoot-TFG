package com.centros_sass.app.repository.catalogs.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.address.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    boolean existsByCityName(String cityName);

    boolean existsByCityNameAndIdNot(String cityName, Integer id);

}
