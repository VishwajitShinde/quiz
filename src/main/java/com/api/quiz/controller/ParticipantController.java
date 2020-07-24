package com.api.quiz.controller;

import com.api.quiz.entity.Participant;
import com.api.quiz.service.ParticipantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = { RequestMethod.POST, RequestMethod.GET , RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.TRACE, RequestMethod.OPTIONS,  } )
public class ParticipantController {
    @Autowired
    private ParticipantService service;

    private Logger logger = LoggerFactory. getLogger(ParticipantController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/registerParticipant", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Participant registerParticipant(@RequestBody Participant participant) {

        try {
            logger.info(" controller : /api/registerParticipant, Register Participant Request Body : {} ", mapper.writeValueAsString( participant ));
            return service.saveParticipant(participant);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException( e );
        }
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
