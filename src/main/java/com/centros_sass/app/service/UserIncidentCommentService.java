package com.centros_sass.app.service;

import java.util.List;
import java.util.Optional;

import com.centros_sass.app.dto.incidents.UserIncidentCommentRequestDTO;
import com.centros_sass.app.dto.incidents.UserIncidentCommentResponseDTO;
import com.centros_sass.app.dto.incidents.UserIncidentCommentUpdateDTO;

public interface UserIncidentCommentService {

    List<UserIncidentCommentResponseDTO> findByIncidentId(Integer incidentId);

    Optional<UserIncidentCommentResponseDTO> findById(Integer id);

    UserIncidentCommentResponseDTO save(Integer incidentId, UserIncidentCommentRequestDTO dto);

    Optional<UserIncidentCommentResponseDTO> update(Integer id, UserIncidentCommentUpdateDTO dto);

    void delete(Integer id);

}
