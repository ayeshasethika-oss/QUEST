package com.quest.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "interview_questions")
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long interviewId;

    @Column(length = 1000, nullable = false)
    private String questionText;

    private String questionType; // Technical, HR, Behavioral, Situational

    public InterviewQuestion() {}

    public InterviewQuestion(Long interviewId, String questionText, String questionType) {
        this.interviewId = interviewId;
        this.questionText = questionText;
        this.questionType = questionType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInterviewId() { return interviewId; }
    public void setInterviewId(Long interviewId) { this.interviewId = interviewId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getQuestionType() { return questionType; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }
}
