package com.service.quiz.repository;

import com.service.quiz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailOrMobile(String email, String mobile );

	Boolean existsByMobile(String mobile);

	Boolean existsByEmail(String email);
}