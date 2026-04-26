package com.centros_sass.app.repository;

import com.centros_sass.app.model.catalogs.dynamic.people.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {

    boolean existsByRelationshipName(String relationshipName);

    boolean existsByRelationshipNameAndIdNot(String relationshipName, Integer id);

}