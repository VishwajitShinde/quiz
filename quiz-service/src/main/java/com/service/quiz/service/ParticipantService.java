package com.service.quiz.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.quiz.entity.Participant;
import com.service.quiz.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepository repository;

    private final Logger logger = LoggerFactory. getLogger(ParticipantService.class);

    private ObjectMapper mapper = new ObjectMapper();

    /**
     *
     * @param participant
     * @return
     */
    public Participant saveParticipant(Participant participant) {
        Participant dbParticipant = repository.findByNameAndEmail( participant.getName(), participant.getEmail() );
        logger.info(" Registration Check Existing Participent : {} ", participant );
        participant =  ( dbParticipant != null ) ? dbParticipant : repository.save(participant);
        logger.info(" Participant : {} > Registration Successful. ", participant );
        return participant;
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
