package com.api.quiz.controller;

import com.api.quiz.entity.Participant;
import com.api.quiz.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class ParticipantController {
    @Autowired
    private ParticipantService service;

    @PostMapping("/addParticipant")
    public Participant addParticipant(@RequestBody Participant participant) {
        return service.saveParticipant(participant);
    }

    @PostMapping("/addParticipants")
    public List<Participant> addParticipants(@RequestBody List<Participant> Participants) {
        return service.saveParticipants(Participants);
    }

    @GetMapping("/participants")
    public List<Participant> findAllParticipants() {
        return service.getParticipants();
    }

    @GetMapping("/participantById/{id}")
    public Participant findParticipantById(@PathVariable int id) {
        return service.getParticipantById(id);
    }

    @GetMapping("/participant/{name}")
    public Participant findParticipantByName(@PathVariable String name) {
        return service.getParticipantByName(name);
    }

    @PutMapping("/update")
    public Participant updateParticipant(@RequestBody Participant participant) {
        return service.updateParticipant(participant);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteParticipant(@PathVariable int id) {
        return service.deleteParticipant(id);
    }
    
}
