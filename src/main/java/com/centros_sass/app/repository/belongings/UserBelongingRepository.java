package com.centros_sass.app.repository.belongings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.belongings.UserBelonging;

@Repository
public interface UserBelongingRepository extends JpaRepository<UserBelonging, Integer> {

    Page<UserBelonging> findAllByIsActiveTrue(Pageable pageable);

    Page<UserBelonging> findAllByIsActiveFalse(Pageable pageable);

    Page<UserBelonging> findAllByUserId(Integer userId, Pageable pageable);

    Page<UserBelonging> findAllByWorkerId(Integer workerId, Pageable pageable);

}
