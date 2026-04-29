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
        if (authentication == null || !authentication.isAuthenticated()) return false;

        // Los roles con privilegios de gestión pasan directamente sin comprobar ownership.
        // Con OAuth2 Resource Server el principal es un Jwt, no un UserDetails/WorkerSecurity.
        if (hasRole(authentication, "ROLE_ADMIN")
                || hasRole(authentication, "ROLE_DIRECTOR")
                || hasRole(authentication, "ROLE_COORDINADOR")) {
            return true;
        }

        // Para roles sin privilegios de gestión, comparar el claim "id" del JWT
        // con el workerId recibido — permite que un worker acceda solo a sus propios datos.
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.oauth2.jwt.Jwt jwt) {
            Object idClaim = jwt.getClaim("id");
            if (idClaim != null) {
                // getClaim devuelve Number (puede ser Integer o Long según Nimbus)
                Integer jwtWorkerId = ((Number) idClaim).intValue();
                return jwtWorkerId.equals(workerId);
            }
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
        if (hasRole(authentication, "ROLE_ADMIN")
                || hasRole(authentication, "ROLE_DIRECTOR")
                || hasRole(authentication, "ROLE_COORDINADOR")) {
            return true;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.oauth2.jwt.Jwt jwt) {
            Object idClaim = jwt.getClaim("id");
            if (idClaim != null) {
                Integer jwtWorkerId = ((Number) idClaim).intValue();
                return jwtWorkerId.equals(ownerWorkerId);
            }
        }
        return false;
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
