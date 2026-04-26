package com.centros_sass.app.repository.belongings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.belongings.UserDiaper;

@Repository
public interface UserDiaperRepository extends JpaRepository<UserDiaper, Integer> {

    Page<UserDiaper> findAllByIsActiveTrue(Pageable pageable);

    Page<UserDiaper> findAllByIsActiveFalse(Pageable pageable);

    Page<UserDiaper> findAllByDiaperSizeId(Integer diaperSizeId, Pageable pageable);

    Page<UserDiaper> findAllByDiaperTypeId(Integer diaperTypeId, Pageable pageable);

}
