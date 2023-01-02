package com.poc.banking.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

public class SecurityUtil {

    public static final UUID getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AccessDeniedException("Access denied");
        }

        UUID userId = UUID.fromString(((JwtAuthenticationToken) authentication).getTokenAttributes().get("userId").toString());
        return userId;
    }

}
