package com.api.quiz.service;

import com.api.quiz.entity.Participant;
import com.api.quiz.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepository repository;

    public Participant saveParticipant(Participant participant) {
        return repository.save(participant);
    }

    public List<Participant> saveParticipants(List<Participant> participants) {
        return repository.saveAll(participants);
    }

    public List<Participant> getParticipants() {
        return repository.findAll();
    }

    public Participant getParticipantById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Participant getParticipantByName(String name) {
        return repository.findByName(name);
    }

    public String deleteParticipant(int id) {
        repository.deleteById(id);
        return "participant removed !! " + id;
    }

    public Participant updateParticipant(Participant participant) {
        Participant existingParticipant = null;
        /*existingParticipant = repository.findById(participant.getParticipantId()).orElse(null);
        existingParticipant.setName(participant.getName());
        existingParticipant.setQuantity(participant.getQuantity());
        existingParticipant.setPrice(participant.getPrice());
        */
        return repository.save(existingParticipant);
    }


}
