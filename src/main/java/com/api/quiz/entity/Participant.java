package com.api.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PARTICIPANT")
public class Participant {

    @Id
    @GeneratedValue
    private int participantId;
    private String name;
    private String email;
    private int score;
    private int timeSpent;
}
