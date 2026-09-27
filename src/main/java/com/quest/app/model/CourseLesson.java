package com.quest.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_lessons")
public class CourseLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long courseId;

    private Integer lessonOrder;
    private String title;
    
    @Column(length = 4000)
    private String contentBody;

    private Integer durationMinutes;

    public CourseLesson() {}

    public CourseLesson(Long courseId, Integer lessonOrder, String title, String contentBody, Integer durationMinutes) {
        this.courseId = courseId;
        this.lessonOrder = lessonOrder;
        this.title = title;
        this.contentBody = contentBody;
        this.durationMinutes = durationMinutes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public Integer getLessonOrder() { return lessonOrder; }
    public void setLessonOrder(Integer lessonOrder) { this.lessonOrder = lessonOrder; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContentBody() { return contentBody; }
    public void setContentBody(String contentBody) { this.contentBody = contentBody; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
}
