package com.api.quiz.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "question_paper_questions" )
public class QuestionPaperQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "exa_setter_id" )
    private long examSetterId;

    @Column(name = "question_text", unique = true )
    private String questionText;

    @Column(name = "option_1" )
    private String option1;

    @Column(name = "option_2" )
    private String option2;

    @Column(name = "option_3" )
    private String option3;

    @Column(name = "option_4" )
    private String option4;

    @Column(name = "answer" )
    private String answer;

    @Column(name = "creation_time" )
    private Date creationTime;

    @Column(name = "last_modified_time" )
    private Date lastModifiedTime;



}
