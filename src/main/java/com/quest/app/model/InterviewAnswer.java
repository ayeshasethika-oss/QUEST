package com.quest.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "interview_answers")
public class InterviewAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long interviewId;

    @Column(nullable = false)
    private Long questionId;

    @Column(length = 4000)
    private String userAnswer;

    private Integer score; // 0-100

    @Column(length = 2000)
    private String feedback;

    public InterviewAnswer() {}

    public InterviewAnswer(Long interviewId, Long questionId, String userAnswer, Integer score, String feedback) {
        this.interviewId = interviewId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.score = score;
        this.feedback = feedback;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInterviewId() { return interviewId; }
    public void setInterviewId(Long interviewId) { this.interviewId = interviewId; }

    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
