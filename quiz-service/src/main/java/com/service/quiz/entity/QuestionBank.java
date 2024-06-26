package com.service.quiz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "question_bank" )
public class QuestionBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "exa_setter_id" )
    private Long examSetterId;

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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "creation_time" )
    private Date creationTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_modified_time" )
    private Date lastModifiedTime;

    public QuestionBank(String questionText, String option1, String option2, String option3, String option4, String answer) {
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;

        this.creationTime = new Date();
        this.lastModifiedTime = new Date();
    }

    @PrePersist
    public void creationTimeSet() {
        this.creationTime = new Date();
        this.lastModifiedTime = new Date();
    }

    @PreUpdate
    public void modificationTimeSet() {
        this.lastModifiedTime = new Date();
    }
}
