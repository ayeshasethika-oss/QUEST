package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_progress")
public class CourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long courseId;

    private Integer completedLessons;
    private Boolean isCompleted;
    private Integer assessmentScore;
    private LocalDateTime completedDate;

    public CourseProgress() {
        this.completedLessons = 0;
        this.isCompleted = false;
        this.assessmentScore = 0;
    }

    public CourseProgress(Long userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
        this.completedLessons = 0;
        this.isCompleted = false;
        this.assessmentScore = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public Integer getCompletedLessons() { return completedLessons; }
    public void setCompletedLessons(Integer completedLessons) { this.completedLessons = completedLessons; }

    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    public Integer getAssessmentScore() { return assessmentScore; }
    public void setAssessmentScore(Integer assessmentScore) { this.assessmentScore = assessmentScore; }

    public LocalDateTime getCompletedDate() { return completedDate; }
    public void setCompletedDate(LocalDateTime completedDate) { this.completedDate = completedDate; }
}
