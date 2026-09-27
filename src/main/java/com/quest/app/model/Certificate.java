package com.quest.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String certificateId; // e.g. CERT-88412

    @Column(nullable = false)
    private Long userId;

    private String userName;
    private String questProfileId;
    private Long courseId;
    private String courseTitle;
    private String skillsLearned;
    private LocalDate issueDate;
    private String pdfPath;

    public Certificate() {
        this.issueDate = LocalDate.now();
    }

    public Certificate(String certificateId, Long userId, String userName, String questProfileId, Long courseId, String courseTitle, String skillsLearned, String pdfPath) {
        this.certificateId = certificateId;
        this.userId = userId;
        this.userName = userName;
        this.questProfileId = questProfileId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.skillsLearned = skillsLearned;
        this.issueDate = LocalDate.now();
        this.pdfPath = pdfPath;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCertificateId() { return certificateId; }
    public void setCertificateId(String certificateId) { this.certificateId = certificateId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getQuestProfileId() { return questProfileId; }
    public void setQuestProfileId(String questProfileId) { this.questProfileId = questProfileId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getSkillsLearned() { return skillsLearned; }
    public void setSkillsLearned(String skillsLearned) { this.skillsLearned = skillsLearned; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }
}
