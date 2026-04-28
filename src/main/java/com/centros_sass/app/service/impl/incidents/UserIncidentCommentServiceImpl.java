package com.centros_sass.app.service.impl.incidents;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.incidents.UserIncidentCommentRequestDTO;
import com.centros_sass.app.dto.incidents.UserIncidentCommentResponseDTO;
import com.centros_sass.app.dto.incidents.UserIncidentCommentUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.incidents.UserIncidentCommentMapper;
import com.centros_sass.app.model.incidents.user.UserIncident;
import com.centros_sass.app.model.incidents.user.UserIncidentComment;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.incidents.UserIncidentCommentRepository;
import com.centros_sass.app.repository.incidents.UserIncidentRepository;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.core.security.WorkerSecurity;
import com.centros_sass.app.service.UserIncidentCommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserIncidentCommentServiceImpl implements UserIncidentCommentService {

    private final UserIncidentCommentRepository commentRepository;
    private final UserIncidentRepository incidentRepository;
    private final WorkerRepository workerRepository;
    private final UserIncidentCommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserIncidentCommentResponseDTO> findByIncidentId(Integer incidentId) {
        return commentRepository.findByUserIncidentIdOrderByCreatedAtDesc(incidentId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserIncidentCommentResponseDTO> findById(Integer id) {
        return commentRepository.findById(id)
                .map(commentMapper::toResponse);
    }

    @Override
    @Transactional
    public UserIncidentCommentResponseDTO save(Integer incidentId, UserIncidentCommentRequestDTO dto) {
        UserIncident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("UserIncident", "id", incidentId));

        Worker currentWorker = getCurrentWorker();

        UserIncidentComment comment = commentMapper.toEntity(dto);
        comment.setWorker(currentWorker);
        comment.setUserIncident(incident);

        UserIncidentComment saved = commentRepository.save(comment);
        return commentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserIncidentCommentResponseDTO> update(Integer id, UserIncidentCommentUpdateDTO dto) {
        return commentRepository.findById(id).map(existing -> {
            commentMapper.updateFromDto(dto, existing);
            UserIncidentComment saved = commentRepository.saveAndFlush(existing);
            return commentMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        UserIncidentComment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserIncidentComment", "id", id));
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
