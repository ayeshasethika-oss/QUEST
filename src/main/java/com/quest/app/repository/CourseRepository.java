package com.quest.app.repository;

import com.quest.app.model.Course;
import com.quest.app.model.CourseLesson;
import com.quest.app.model.CourseAssessment;
import com.quest.app.model.CourseProgress;
import com.quest.app.model.Certificate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCategory(String category);
}

@Repository
interface CourseLessonRepositoryInternal extends JpaRepository<CourseLesson, Long> {
    List<CourseLesson> findByCourseIdOrderByLessonOrderAsc(Long courseId);
}
