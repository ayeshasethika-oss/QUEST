package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resume_analysis")
public class ResumeAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private Long resumeId;

    private Integer overallScore; // 0 - 100
    private Integer completenessScore;

    @Column(length = 1500)
    private String detectedSkills;

    @Column(length = 1500)
    private String missingSkills;

    @Column(length = 2000)
    private String strengths;

    @Column(length = 2000)
    private String improvementSuggestions;

    private String careerCompatibility;
    
    private LocalDateTime analyzedAt;

    public ResumeAnalysis() {
        this.analyzedAt = LocalDateTime.now();
    }

    public ResumeAnalysis(Long userId, Long resumeId, Integer overallScore, Integer completenessScore, String detectedSkills, String missingSkills, String strengths, String improvementSuggestions, String careerCompatibility) {
        this.userId = userId;
        this.resumeId = resumeId;
        this.overallScore = overallScore;
        this.completenessScore = completenessScore;
        this.detectedSkills = detectedSkills;
        this.missingSkills = missingSkills;
        this.strengths = strengths;
        this.improvementSuggestions = improvementSuggestions;
        this.careerCompatibility = careerCompatibility;
        this.analyzedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getResumeId() { return resumeId; }
    public void setResumeId(Long resumeId) { this.resumeId = resumeId; }

    public Integer getOverallScore() { return overallScore; }
    public void setOverallScore(Integer overallScore) { this.overallScore = overallScore; }

    public Integer getCompletenessScore() { return completenessScore; }
    public void setCompletenessScore(Integer completenessScore) { this.completenessScore = completenessScore; }

    public String getDetectedSkills() { return detectedSkills; }
    public void setDetectedSkills(String detectedSkills) { this.detectedSkills = detectedSkills; }

    public String getMissingSkills() { return missingSkills; }
    public void setMissingSkills(String missingSkills) { this.missingSkills = missingSkills; }

    public String getStrengths() { return strengths; }
    public void setStrengths(String strengths) { this.strengths = strengths; }

    public String getImprovementSuggestions() { return improvementSuggestions; }
    public void setImprovementSuggestions(String improvementSuggestions) { this.improvementSuggestions = improvementSuggestions; }

    public String getCareerCompatibility() { return careerCompatibility; }
    public void setCareerCompatibility(String careerCompatibility) { this.careerCompatibility = careerCompatibility; }

    public LocalDateTime getAnalyzedAt() { return analyzedAt; }
    public void setAnalyzedAt(LocalDateTime analyzedAt) { this.analyzedAt = analyzedAt; }
}
