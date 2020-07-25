package com.api.quiz.service;

import com.api.quiz.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActiveUserDetailRetrivalService {

    public String getEmail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) securityContext.getAuthentication().getPrincipal();
        return userPrincipal.getEmail();
    }

    public String getMobile() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) securityContext.getAuthentication().getPrincipal();
        return userPrincipal.getMobile();
    }

    public Long getId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) securityContext.getAuthentication().getPrincipal();
        return userPrincipal.getId();
    }

    public Set<String> getRoles() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) securityContext.getAuthentication().getPrincipal();
        return userPrincipal.getAuthorities().stream()
                .map( simpleGrantedAuthority -> simpleGrantedAuthority.getAuthority() ).collect(Collectors.toSet());
    }
}
