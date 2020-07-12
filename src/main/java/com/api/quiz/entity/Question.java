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
@Table(name = "QUESTIONBANK")
public class Question {

    @Id
    @GeneratedValue
    private int id;
    private int exaSetterId;
    private String queText;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;
    private String answer;

}
