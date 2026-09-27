package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "career_profiles")
public class CareerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    private String education;
    private String currentStatus; // Student, Employed, Unemployed, Career Break, Other
    private String careerGoal;
    
    @Column(length = 1000)
    private String currentSkills;
    
    @Column(length = 1000)
    private String interestedSkills;

    private String experience;
    private String internships;
    private String certifications;
    private String projects;
    private Boolean careerBreak;
    private String careerBreakReason;
    private String preferredCareerArea;

    private LocalDateTime updatedAt;

    public CareerProfile() {
        this.updatedAt = LocalDateTime.now();
    }

    public CareerProfile(Long userId) {
        this.userId = userId;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }

    public String getCareerGoal() { return careerGoal; }
    public void setCareerGoal(String careerGoal) { this.careerGoal = careerGoal; }

    public String getCurrentSkills() { return currentSkills; }
    public void setCurrentSkills(String currentSkills) { this.currentSkills = currentSkills; }

    public String getInterestedSkills() { return interestedSkills; }
    public void setInterestedSkills(String interestedSkills) { this.interestedSkills = interestedSkills; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getInternships() { return internships; }
    public void setInternships(String internships) { this.internships = internships; }

    public String getCertifications() { return certifications; }
    public void setCertifications(String certifications) { this.certifications = certifications; }

    public String getProjects() { return projects; }
    public void setProjects(String projects) { this.projects = projects; }

    public Boolean getCareerBreak() { return careerBreak; }
    public void setCareerBreak(Boolean careerBreak) { this.careerBreak = careerBreak; }

    public String getCareerBreakReason() { return careerBreakReason; }
    public void setCareerBreakReason(String careerBreakReason) { this.careerBreakReason = careerBreakReason; }

    public String getPreferredCareerArea() { return preferredCareerArea; }
    public void setPreferredCareerArea(String preferredCareerArea) { this.preferredCareerArea = preferredCareerArea; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
