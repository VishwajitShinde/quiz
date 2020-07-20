package com.api.quiz.repository;

import java.util.Optional;

import com.api.quiz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameOrEmail(String username, String email );

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}