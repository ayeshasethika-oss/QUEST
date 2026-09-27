package com.quest.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    private Boolean opportunityAlerts;
    private Boolean learningReminders;
    private Boolean healthReminders;
    private Boolean menstrualReminders;

    public NotificationPreference() {
        this.opportunityAlerts = true;
        this.learningReminders = true;
        this.healthReminders = true;
        this.menstrualReminders = true;
    }

    public NotificationPreference(Long userId) {
        this.userId = userId;
        this.opportunityAlerts = true;
        this.learningReminders = true;
        this.healthReminders = true;
        this.menstrualReminders = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Boolean getOpportunityAlerts() { return opportunityAlerts; }
    public void setOpportunityAlerts(Boolean opportunityAlerts) { this.opportunityAlerts = opportunityAlerts; }

    public Boolean getLearningReminders() { return learningReminders; }
    public void setLearningReminders(Boolean learningReminders) { this.learningReminders = learningReminders; }

    public Boolean getHealthReminders() { return healthReminders; }
    public void setHealthReminders(Boolean healthReminders) { this.healthReminders = healthReminders; }

    public Boolean getMenstrualReminders() { return menstrualReminders; }
    public void setMenstrualReminders(Boolean menstrualReminders) { this.menstrualReminders = menstrualReminders; }
}
