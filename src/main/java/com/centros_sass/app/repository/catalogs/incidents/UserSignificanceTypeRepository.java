package com.centros_sass.app.repository.catalogs.incidents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.catalogs.incidents.user.UserSignificanceType;

@Repository
public interface UserSignificanceTypeRepository extends JpaRepository<UserSignificanceType, Integer> {

    boolean existsBySignificanceName(String significanceName);

    boolean existsBySignificanceNameAndIdNot(String significanceName, Integer id);

}
