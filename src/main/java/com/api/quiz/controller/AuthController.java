package com.api.quiz.controller;

import com.api.quiz.models.ERole;
import com.api.quiz.models.Role;
import com.api.quiz.models.User;
import com.api.quiz.payload.request.LoginRequest;
import com.api.quiz.payload.request.SignupRequest;
import com.api.quiz.payload.response.JwtResponse;
import com.api.quiz.payload.response.MessageResponse;
import com.api.quiz.repository.RoleRepository;
import com.api.quiz.repository.UserRepository;
import com.api.quiz.security.jwt.JwtUtils;
import com.api.quiz.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	Logger logger = LoggerFactory.getLogger(AuthController.class);

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

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		logger.info( "Controller [api/auth/signin] Login Request : {} ", loginRequest  );

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrMobile(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		logger.info( "Controller [api/auth/signin] jwt Token Generated : {} ", jwt  );
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		logger.info( "Controller [api/auth/signin] roles : {} ", roles  );
		return ResponseEntity.ok(new JwtResponse(jwt,  userDetails));
	}

	/**
	 *
	 * @param signUpRequest
	 * @return
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		logger.info(" Controller [/api/auth/signup] request : {} ", signUpRequest );

		if (userRepository.existsByMobile(signUpRequest.getMobile())) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(new MessageResponse("Error: Mobile number already exist!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(
							 signUpRequest.getEmail(),
				             encoder.encode(signUpRequest.getPassword()),
				             signUpRequest.getMobile(),
							 signUpRequest.getFirstName(),
							 signUpRequest.getLastName()  );

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			strRoles = new HashSet<>();
			strRoles.add(userRole.getName().name());
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_ADMIN" : case "ROLE_MODERATOR" : case "ROLE_TEACHER" :
					case "ROLE_STUDENT" :
					Role adminRole = roleRepository.findByName( ERole.valueOf( role ) )
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		user = userRepository.save(user);
		logger.info(" Sign Up  Successful For User : {} ", user.getEmail() ); //TODO Need to remove this Log
		JwtResponse jwtResponse = new JwtResponse(null, user );
		return ResponseEntity.ok(new MessageResponse("User registered successfully!", jwtResponse));
	}

	/**
	 * User Must Send Token
	 * @return
	 */
	@PostMapping("/logout")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STUDENT') ")
	public ResponseEntity logoutUser( ) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		UserDetailsImpl userPrincipal = (UserDetailsImpl) securityContext.getAuthentication().getPrincipal();
		securityContext.setAuthentication(null);
		return ResponseEntity.ok(new MessageResponse("logout successful", userPrincipal));
	}

}