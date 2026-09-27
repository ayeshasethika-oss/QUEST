package com.quest.app.controller;

import com.quest.app.model.User;
import com.quest.app.model.CourseProgress;
import com.quest.app.service.CourseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

    @Autowired
    private CourseService courseService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(Map.of("success", true, "data", courseService.getAllCourses()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("success", true, "data", courseService.getCourseById(id).orElseThrow()));
    }

    @GetMapping("/{id}/lessons")
    public ResponseEntity<?> getLessons(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("success", true, "data", courseService.getLessonsForCourse(id)));
    }

    @PostMapping("/{id}/progress")
    public ResponseEntity<?> updateProgress(@PathVariable Long id, @RequestBody Map<String, Integer> body, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        Integer lessonOrder = body.get("lessonOrder");
        CourseProgress progress = courseService.completeLesson(user.getId(), id, lessonOrder);
        return ResponseEntity.ok(Map.of("success", true, "data", progress));
    }

    @PostMapping("/{id}/assessment")
    public ResponseEntity<?> submitAssessment(@PathVariable Long id, @RequestBody Map<String, List<String>> body, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        List<String> answers = body.get("answers");
        Map<String, Object> result = courseService.submitAssessment(user.getId(), id, answers);
        return ResponseEntity.ok(result);
    }
}
