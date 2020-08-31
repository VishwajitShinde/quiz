package com.service.quiz.repository;

import com.service.quiz.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant,Integer> {
    Participant findByName(String name);
    Participant findByNameAndEmail(String name, String email);
}
