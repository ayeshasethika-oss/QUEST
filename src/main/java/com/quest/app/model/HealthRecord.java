package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "health_records")
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private LocalDate recordDate;
    private Integer hydrationGlasses; // e.g., 8
    private Double sleepHours; // e.g., 7.5
    private Integer activityMinutes; // e.g., 30
    private String stressLevel; // Low, Moderate, High
    private String notes;

    public HealthRecord() {
        this.recordDate = LocalDate.now();
    }

    public HealthRecord(Long userId, Integer hydrationGlasses, Double sleepHours, Integer activityMinutes, String stressLevel, String notes) {
        this.userId = userId;
        this.hydrationGlasses = hydrationGlasses;
        this.sleepHours = sleepHours;
        this.activityMinutes = activityMinutes;
        this.stressLevel = stressLevel;
        this.notes = notes;
        this.recordDate = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDate getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }

    public Integer getHydrationGlasses() { return hydrationGlasses; }
    public void setHydrationGlasses(Integer hydrationGlasses) { this.hydrationGlasses = hydrationGlasses; }

    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }

    public Integer getActivityMinutes() { return activityMinutes; }
    public void setActivityMinutes(Integer activityMinutes) { this.activityMinutes = activityMinutes; }

    public String getStressLevel() { return stressLevel; }
    public void setStressLevel(String stressLevel) { this.stressLevel = stressLevel; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
