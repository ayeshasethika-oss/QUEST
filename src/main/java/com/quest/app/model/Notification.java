package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String title;

    @Column(length = 1000, nullable = false)
    private String message;

    private String category; // Opportunity, Course, Health, Menstrual, System
    private Boolean isRead;
    private Boolean isFemaleOnly;
    private LocalDateTime createdAt;

    public Notification() {
        this.isRead = false;
        this.isFemaleOnly = false;
        this.createdAt = LocalDateTime.now();
    }

    public Notification(Long userId, String title, String message, String category, Boolean isFemaleOnly) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.category = category;
        this.isRead = false;
        this.isFemaleOnly = isFemaleOnly != null ? isFemaleOnly : false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public Boolean getIsFemaleOnly() { return isFemaleOnly; }
    public void setIsFemaleOnly(Boolean isFemaleOnly) { this.isFemaleOnly = isFemaleOnly; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
