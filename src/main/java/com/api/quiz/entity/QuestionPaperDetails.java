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
@Table(	name = "question_paper_details",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "question_paper_id"),
                @UniqueConstraint(columnNames = "question_id")
        })
public class QuestionPaperDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "question_paper_id" )
    private int questionPaperId;

    @NotBlank
    @Column(name = "question_id" )
    private int questionId;

    @Column(name = "creation_time" )
    private Date creationTime;

    @Column(name = "last_modified_time" )
    private Date lastModifiedTime;

    public QuestionPaperDetails(@NotBlank int questionPaperId,  @NotBlank int questionId ) {

        this.questionId = questionId;
        this.questionPaperId = questionPaperId;

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
