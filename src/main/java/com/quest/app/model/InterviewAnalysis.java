package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interview_analysis")
public class InterviewAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long interviewId;

    @Column(nullable = false)
    private Long userId;

    private Integer overallScore;
    private Integer knowledgeScore;
    private Integer relevanceScore;

    @Column(length = 1500)
    private String missingSkills;

    @Column(length = 3000)
    private String improvementPlan;

    private LocalDateTime createdDate;

    public InterviewAnalysis() {
        this.createdDate = LocalDateTime.now();
    }

    public InterviewAnalysis(Long interviewId, Long userId, Integer overallScore, Integer knowledgeScore, Integer relevanceScore, String missingSkills, String improvementPlan) {
        this.interviewId = interviewId;
        this.userId = userId;
        this.overallScore = overallScore;
        this.knowledgeScore = knowledgeScore;
        this.relevanceScore = relevanceScore;
        this.missingSkills = missingSkills;
        this.improvementPlan = improvementPlan;
        this.createdDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInterviewId() { return interviewId; }
    public void setInterviewId(Long interviewId) { this.interviewId = interviewId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getOverallScore() { return overallScore; }
    public void setOverallScore(Integer overallScore) { this.overallScore = overallScore; }

    public Integer getKnowledgeScore() { return knowledgeScore; }
    public void setKnowledgeScore(Integer knowledgeScore) { this.knowledgeScore = knowledgeScore; }

    public Integer getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(Integer relevanceScore) { this.relevanceScore = relevanceScore; }

    public String getMissingSkills() { return missingSkills; }
    public void setMissingSkills(String missingSkills) { this.missingSkills = missingSkills; }

    public String getImprovementPlan() { return improvementPlan; }
    public void setImprovementPlan(String improvementPlan) { this.improvementPlan = improvementPlan; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
