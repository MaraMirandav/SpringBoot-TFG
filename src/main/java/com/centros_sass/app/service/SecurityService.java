package com.centros_sass.app.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.centros_sass.app.security.WorkerSecurity;

@Service("securityService")
public class SecurityService {

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

    public boolean isCommentAuthorOrAdmin(Integer commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        return hasRole(authentication, "ROLE_ADMIN") || hasRole(authentication, "ROLE_DIRECTOR");
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
