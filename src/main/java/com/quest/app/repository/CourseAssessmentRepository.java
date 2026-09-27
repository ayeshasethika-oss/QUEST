package com.quest.app.repository;

import com.quest.app.model.CourseAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseAssessmentRepository extends JpaRepository<CourseAssessment, Long> {
    List<CourseAssessment> findByCourseId(Long courseId);
}
