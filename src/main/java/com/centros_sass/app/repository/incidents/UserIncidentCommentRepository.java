package com.centros_sass.app.repository.incidents;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.incidents.user.UserIncidentComment;

@Repository
public interface UserIncidentCommentRepository extends JpaRepository<UserIncidentComment, Integer> {

    List<UserIncidentComment> findByUserIncidentIdOrderByCreatedAtDesc(Integer userIncidentId);

}
