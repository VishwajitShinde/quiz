package com.api.quiz.repository;

import java.util.Optional;

import com.api.quiz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailOrMobile(String email, String mobile );

	Boolean existsByMobile(String mobile);

	Boolean existsByEmail(String email);
}