package com.quest.app.controller;

import com.quest.app.model.User;
import com.quest.app.service.CareerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/career")
public class CareerRestController {

    @Autowired
    private CareerService careerService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @GetMapping("/recommendations")
    public ResponseEntity<?> getRecommendations(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        Map<String, Object> analysis = careerService.analyzeCareer(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "data", analysis));
    }

    @GetMapping("/roadmap")
    public ResponseEntity<?> getRoadmap(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        Map<String, Object> analysis = careerService.analyzeCareer(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "data", analysis.get("roadmap")));
    }

    @GetMapping("/skill-gap")
    public ResponseEntity<?> getSkillGap(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        Map<String, Object> analysis = careerService.analyzeCareer(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "missingSkills", analysis.get("missingSkills"), "recommendedSkills", analysis.get("recommendedSkills")));
    }

    @GetMapping("/restart")
    public ResponseEntity<?> getCareerRestart(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        Map<String, Object> restart = careerService.analyzeCareerRestart(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "data", restart));
    }
}
