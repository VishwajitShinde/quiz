package com.service.quiz.repository;

import com.service.quiz.models.ERole;
import com.service.quiz.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}

