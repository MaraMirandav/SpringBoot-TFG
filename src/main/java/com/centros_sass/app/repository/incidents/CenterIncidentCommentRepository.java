package com.centros_sass.app.repository.incidents;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.incidents.center.CenterIncidentComment;

@Repository
public interface CenterIncidentCommentRepository extends JpaRepository<CenterIncidentComment, Integer> {

    List<CenterIncidentComment> findByCdIncidentIdOrderByCreatedAtDesc(Integer cdIncidentId);

}
