package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "opportunities")
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String organization;

    @Column(length = 2000)
    private String description;

    private String category; // Job, Internship, Competition, Scholarship, Recruitment Drive, Training Program
    private String eligibility;
    private String requiredSkills;
    private String location;
    private String mode; // Remote, On-site, Hybrid
    private LocalDate deadline;
    private LocalDate postedDate;

    public Opportunity() {
        this.postedDate = LocalDate.now();
    }

    public Opportunity(String title, String organization, String description, String category, String eligibility, String requiredSkills, String location, String mode, LocalDate deadline) {
        this.title = title;
        this.organization = organization;
        this.description = description;
        this.category = category;
        this.eligibility = eligibility;
        this.requiredSkills = requiredSkills;
        this.location = location;
        this.mode = mode;
        this.deadline = deadline;
        this.postedDate = LocalDate.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getEligibility() { return eligibility; }
    public void setEligibility(String eligibility) { this.eligibility = eligibility; }

    public String getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public LocalDate getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }
}
