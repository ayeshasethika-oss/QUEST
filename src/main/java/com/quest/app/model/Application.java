package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long opportunityId;

    private String opportunityTitle;
    private String organization;
    private LocalDate applicationDate;
    
    // Statuses: Applied, Under Review, Shortlisted, Selected, Rejected, Completed
    @Column(nullable = false)
    private String status;

    private LocalDate deadline;

    public Application() {
        this.applicationDate = LocalDate.now();
        this.status = "Applied";
    }

    public Application(Long userId, Long opportunityId, String opportunityTitle, String organization, LocalDate deadline) {
        this.userId = userId;
        this.opportunityId = opportunityId;
        this.opportunityTitle = opportunityTitle;
        this.organization = organization;
        this.applicationDate = LocalDate.now();
        this.status = "Applied";
        this.deadline = deadline;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getOpportunityId() { return opportunityId; }
    public void setOpportunityId(Long opportunityId) { this.opportunityId = opportunityId; }

    public String getOpportunityTitle() { return opportunityTitle; }
    public void setOpportunityTitle(String opportunityTitle) { this.opportunityTitle = opportunityTitle; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
}
