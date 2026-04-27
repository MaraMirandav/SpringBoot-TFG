package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.centros_sass.app.model.incidents.center.CenterIncidentComment;
import com.centros_sass.app.model.incidents.user.UserIncidentComment;
import com.centros_sass.app.model.profiles.workers.WorkerSchedule;
import com.centros_sass.app.model.profiles.workers.WorkerScheduleRecord;
import com.centros_sass.app.repository.incidents.CenterIncidentCommentRepository;
import com.centros_sass.app.repository.incidents.UserIncidentCommentRepository;
import com.centros_sass.app.repository.profiles.WorkerScheduleRecordRepository;
import com.centros_sass.app.repository.profiles.WorkerScheduleRepository;
import com.centros_sass.app.security.WorkerSecurity;

import lombok.RequiredArgsConstructor;

@Service("securityService")
@RequiredArgsConstructor
public class SecurityService {

    private final UserIncidentCommentRepository userIncidentCommentRepository;
    private final CenterIncidentCommentRepository centerIncidentCommentRepository;
    private final WorkerScheduleRepository workerScheduleRepository;
    private final WorkerScheduleRecordRepository workerScheduleRecordRepository;

    public boolean isOwnerOrAdmin(Integer workerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof WorkerSecurity workerSecurity) {
            Integer currentWorkerId = workerSecurity.getWorker().getId();

            if (currentWorkerId != null && currentWorkerId.equals(workerId)) {
                return true;
            }
            return hasRole(authentication, "ROLE_ADMIN") || hasRole(authentication, "ROLE_DIRECTOR");
        }
        return false;
    }

    public boolean isUserCommentAuthorOrAdmin(Integer commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Optional<UserIncidentComment> userComment = userIncidentCommentRepository.findById(commentId);
        if (userComment.isEmpty()) {
            return false;
        }

        Integer authorWorkerId = userComment.get().getWorker().getId();
        return isCurrentWorkerOrAdmin(authentication, authorWorkerId);
    }

    public boolean isCenterCommentAuthorOrAdmin(Integer commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Optional<CenterIncidentComment> centerComment = centerIncidentCommentRepository.findById(commentId);
        if (centerComment.isEmpty()) {
            return false;
        }

        Integer authorWorkerId = centerComment.get().getWorker().getId();
        return isCurrentWorkerOrAdmin(authentication, authorWorkerId);
    }

    public boolean isScheduleOwnerOrAdmin(Integer scheduleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Optional<WorkerSchedule> schedule = workerScheduleRepository.findById(scheduleId);
        if (schedule.isEmpty()) {
            return false;
        }

        Integer ownerWorkerId = schedule.get().getWorker().getId();
        return isCurrentWorkerOrAdmin(authentication, ownerWorkerId);
    }

    public boolean isRecordOwnerOrAdmin(Integer recordId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Optional<WorkerScheduleRecord> record = workerScheduleRecordRepository.findById(recordId);
        if (record.isEmpty()) {
            return false;
        }

        Integer ownerWorkerId = record.get().getWorker().getId();
        return isCurrentWorkerOrAdmin(authentication, ownerWorkerId);
    }

    private boolean isCurrentWorkerOrAdmin(Authentication authentication, Integer ownerWorkerId) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof WorkerSecurity workerSecurity) {
            Integer currentWorkerId = workerSecurity.getWorker().getId();
            if (currentWorkerId != null && currentWorkerId.equals(ownerWorkerId)) {
                return true;
            }
        }
        return hasRole(authentication, "ROLE_ADMIN")
                || hasRole(authentication, "ROLE_DIRECTOR")
                || hasRole(authentication, "ROLE_COORDINADOR");
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
