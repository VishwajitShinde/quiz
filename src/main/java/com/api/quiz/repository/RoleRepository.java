package com.api.quiz.repository;

import java.util.Optional;

import com.api.quiz.models.ERole;
import com.api.quiz.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}

