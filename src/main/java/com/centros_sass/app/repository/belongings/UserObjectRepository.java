package com.centros_sass.app.repository.belongings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.belongings.UserObject;

@Repository
public interface UserObjectRepository extends JpaRepository<UserObject, Integer> {

    Page<UserObject> findAllByIsActiveTrue(Pageable pageable);

    Page<UserObject> findAllByIsActiveFalse(Pageable pageable);

    Page<UserObject> findAllByObjectTypeId(Integer objectTypeId, Pageable pageable);

    Page<UserObject> findAllByItemConditionId(Integer itemConditionId, Pageable pageable);

}
