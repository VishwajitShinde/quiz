package com.service.quiz.service;

import com.service.quiz.exception.DataExistException;
import com.service.quiz.exception.RoleNotFoundException;
import com.service.quiz.models.ERole;
import com.service.quiz.models.Role;
import com.service.quiz.models.User;
import com.service.quiz.payload.request.LoginRequest;
import com.service.quiz.payload.request.SignupRequest;
import com.service.quiz.payload.response.JwtResponse;
import com.service.quiz.payload.response.MessageResponse;
import com.service.quiz.repository.RoleRepository;
import com.service.quiz.repository.UserRepository;
import com.service.quiz.security.jwt.JwtUtils;
import com.service.quiz.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * @param loginRequest
     * @return
     */
    public JwtResponse authenticateOrSignInUser(@NotNull @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrMobile(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        logger.info("In Service from Controller [api/auth/signin] jwt Token Generated : {} ", jwt);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        logger.info("In Service from Controller [api/auth/signin] roles : {} ", roles);

        return new JwtResponse(jwt, userDetails);
    }

    /**
     * @param signUpRequest
     * @return
     * @throws DataExistException
     */
    public MessageResponse registerOrSignUpUser(@Valid SignupRequest signUpRequest) throws DataExistException, RoleNotFoundException {
        if (userRepository.existsByMobile(signUpRequest.getMobile())) {
            throw new DataExistException("Mobile number already exist!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DataExistException("Email is already in use!");
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getMobile(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName());

        Set<Role> roles = extractUserRoles(signUpRequest.getRole());
        user.setRoles(roles);
        user = userRepository.save(user);
        logger.info(" Sign Up  Successful For User : {} ", user.getEmail()); //TODO Need to remove this Log
        JwtResponse jwtResponse = new JwtResponse(null, user);
        return new MessageResponse("User registered successfully!", jwtResponse);
    }

    /**
     * @param strRoles
     * @return
     */
    private Set<Role> extractUserRoles(Set<String> strRoles) throws RoleNotFoundException {
        String ROLE_IS_NOT_FOUND = "Role is not found.";

        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException(ROLE_IS_NOT_FOUND));
            roles.add(userRole);
            strRoles = new HashSet<>();
            strRoles.add(userRole.getName().name());
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                    case "ROLE_MODERATOR":
                    case "ROLE_TEACHER":
                    case "ROLE_STUDENT":
                        Role adminRole = roleRepository.findByName(ERole.valueOf(role))
                                .orElseThrow(() -> new RoleNotFoundException(ROLE_IS_NOT_FOUND));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RoleNotFoundException(ROLE_IS_NOT_FOUND));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }


}
