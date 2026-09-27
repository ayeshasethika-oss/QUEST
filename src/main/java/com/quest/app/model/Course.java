package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String category;
    
    @Column(length = 2000)
    private String description;

    private String duration;
    private String level; // Beginner, Intermediate, Advanced
    private String instructor;
    private String imageUrl;
    private Integer totalLessons;
    private Double rating;
    private LocalDateTime createdDate;

    public Course() {
        this.createdDate = LocalDateTime.now();
        this.rating = 4.8;
        this.totalLessons = 3;
    }

    public Course(String title, String category, String description, String duration, String level, String instructor, String imageUrl) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.duration = duration;
        this.level = level;
        this.instructor = instructor;
        this.imageUrl = imageUrl;
        this.totalLessons = 3;
        this.rating = 4.8;
        this.createdDate = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getTotalLessons() { return totalLessons; }
    public void setTotalLessons(Integer totalLessons) { this.totalLessons = totalLessons; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
