package com.centros_sass.app.service.impl.incidents;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.incidents.CenterIncidentCommentRequestDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentCommentResponseDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentCommentUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.incidents.CenterIncidentCommentMapper;
import com.centros_sass.app.model.incidents.center.CenterIncident;
import com.centros_sass.app.model.incidents.center.CenterIncidentComment;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.incidents.CenterIncidentCommentRepository;
import com.centros_sass.app.repository.incidents.CenterIncidentRepository;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.core.security.WorkerSecurity;
import com.centros_sass.app.service.CenterIncidentCommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CenterIncidentCommentServiceImpl implements CenterIncidentCommentService {

    private final CenterIncidentCommentRepository commentRepository;
    private final CenterIncidentRepository incidentRepository;
    private final WorkerRepository workerRepository;
    private final CenterIncidentCommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CenterIncidentCommentResponseDTO> findByIncidentId(Integer incidentId) {
        return commentRepository.findByCdIncidentIdOrderByCreatedAtDesc(incidentId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CenterIncidentCommentResponseDTO> findById(Integer id) {
        return commentRepository.findById(id)
                .map(commentMapper::toResponse);
    }

    @Override
    @Transactional
    public CenterIncidentCommentResponseDTO save(Integer incidentId, CenterIncidentCommentRequestDTO dto) {
        CenterIncident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("CenterIncident", "id", incidentId));

        Worker currentWorker = getCurrentWorker();

        CenterIncidentComment comment = commentMapper.toEntity(dto);
        comment.setWorker(currentWorker);
        comment.setCdIncident(incident);

        CenterIncidentComment saved = commentRepository.save(comment);
        return commentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<CenterIncidentCommentResponseDTO> update(Integer id, CenterIncidentCommentUpdateDTO dto) {
        return commentRepository.findById(id).map(existing -> {
            commentMapper.updateFromDto(dto, existing);
            CenterIncidentComment saved = commentRepository.saveAndFlush(existing);
            return commentMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        CenterIncidentComment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CenterIncidentComment", "id", id));
        commentRepository.delete(comment);
    }

    private Worker getCurrentWorker() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof WorkerSecurity workerSecurity) {
            return workerSecurity.getWorker();
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return workerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "email", email));
    }

}
