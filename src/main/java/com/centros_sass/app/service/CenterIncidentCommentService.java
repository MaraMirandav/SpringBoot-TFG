package com.centros_sass.app.service;

import java.util.List;
import java.util.Optional;

import com.centros_sass.app.dto.incidents.CenterIncidentCommentRequestDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentCommentResponseDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentCommentUpdateDTO;

public interface CenterIncidentCommentService {

    List<CenterIncidentCommentResponseDTO> findByIncidentId(Integer incidentId);

    Optional<CenterIncidentCommentResponseDTO> findById(Integer id);

    CenterIncidentCommentResponseDTO save(Integer incidentId, CenterIncidentCommentRequestDTO dto);

    Optional<CenterIncidentCommentResponseDTO> update(Integer id, CenterIncidentCommentUpdateDTO dto);

    void delete(Integer id);

}
