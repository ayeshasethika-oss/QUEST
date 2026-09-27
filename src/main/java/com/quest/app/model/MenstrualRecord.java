package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "menstrual_records")
public class MenstrualRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate startDate;

    private Integer cycleLengthDays; // default 28
    private Integer periodDurationDays; // default 5
    private String symptoms;
    private String notes;
    private Integer reminderDaysBefore; // default 2

    public MenstrualRecord() {
        this.cycleLengthDays = 28;
        this.periodDurationDays = 5;
        this.reminderDaysBefore = 2;
    }

    public MenstrualRecord(Long userId, LocalDate startDate, Integer cycleLengthDays, Integer periodDurationDays, String symptoms, String notes) {
        this.userId = userId;
        this.startDate = startDate;
        this.cycleLengthDays = cycleLengthDays != null ? cycleLengthDays : 28;
        this.periodDurationDays = periodDurationDays != null ? periodDurationDays : 5;
        this.symptoms = symptoms;
        this.notes = notes;
        this.reminderDaysBefore = 2;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public Integer getCycleLengthDays() { return cycleLengthDays; }
    public void setCycleLengthDays(Integer cycleLengthDays) { this.cycleLengthDays = cycleLengthDays; }

    public Integer getPeriodDurationDays() { return periodDurationDays; }
    public void setPeriodDurationDays(Integer periodDurationDays) { this.periodDurationDays = periodDurationDays; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Integer getReminderDaysBefore() { return reminderDaysBefore; }
    public void setReminderDaysBefore(Integer reminderDaysBefore) { this.reminderDaysBefore = reminderDaysBefore; }
}
