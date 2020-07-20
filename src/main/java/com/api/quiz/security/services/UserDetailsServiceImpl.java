package com.api.quiz.security.services;

import com.api.quiz.models.User;
import com.api.quiz.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		// Let people login with either username or email
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() ->
						new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
				);
		try {
			logger.info( " UserDetailsServiceImpl loadUserByUsername : {} ",  mapper.writeValueAsString(user) );
		} catch (  Exception s) {
			logger.info(" Parsing Exception : {} ", s.getMessage() );
		}

		return UserDetailsImpl.build(user);
	}

}