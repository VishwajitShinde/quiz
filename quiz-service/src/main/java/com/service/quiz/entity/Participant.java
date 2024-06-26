package com.service.quiz.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "participant")
public class Participant {

    @Id
    @GeneratedValue
    private int participantId;
    private String name;
    private String email;
    private int score;
    private int timeSpent;
}
