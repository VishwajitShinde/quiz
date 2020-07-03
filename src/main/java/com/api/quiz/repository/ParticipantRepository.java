package com.api.quiz.repository;

import com.api.quiz.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant,Integer> {
    Participant findByName(String name);
}
