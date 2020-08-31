package com.service.quiz.controller;


import com.service.quiz.exception.DataExistException;
import com.service.quiz.exception.RoleNotFoundException;
import com.service.quiz.payload.request.LoginRequest;
import com.service.quiz.payload.request.SignupRequest;
import com.service.quiz.payload.response.JwtResponse;
import com.service.quiz.payload.response.MessageResponse;
import com.service.quiz.security.services.UserDetailsImpl;
import com.service.quiz.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Controller [api/auth/signin] Login Request : {} ", loginRequest);
        JwtResponse jwtResponse = authService.authenticateOrSignInUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        logger.info(" Controller [/api/auth/signup] request : {} ", signUpRequest);
        try {
            return ResponseEntity.ok(authService.registerOrSignUpUser(signUpRequest));
        } catch (DataExistException dataExistException) {
            logger.error(" Validation Error : {} ", dataExistException.getMessage());
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MessageResponse(dataExistException.getMessage()));
        } catch (RoleNotFoundException roleNotFoundException) {
            logger.error(" Validation Error : {} ", roleNotFoundException);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new MessageResponse(roleNotFoundException.getMessage()));
        }
    }

    /**
     * User Must Send Token
     *
     * @return
     */
    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STUDENT') ")
    public ResponseEntity logoutUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) securityContext.getAuthentication().getPrincipal();
        securityContext.setAuthentication(null);
        return ResponseEntity.ok(new MessageResponse("logout successful", userPrincipal));
    }

}