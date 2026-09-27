package com.quest.app.controller;

import com.quest.app.model.ResumeAnalysis;
import com.quest.app.model.User;
import com.quest.app.service.ResumeAnalysisService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/resume")
public class ResumeRestController {

    @Autowired
    private ResumeAnalysisService resumeAnalysisService;

    @Value("${quest.upload.dir:C:/Users/ELCOT/.gemini/antigravity/scratch/QUEST/uploads}")
    private String uploadDir;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        try {
            ResumeAnalysis analysis = resumeAnalysisService.processAndAnalyzeResume(user.getId(), file, uploadDir);
            return ResponseEntity.ok(Map.of("success", true, "message", "Resume uploaded & analyzed successfully!", "data", analysis));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getLatestAnalysis(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        Optional<ResumeAnalysis> analysis = resumeAnalysisService.getLatestAnalysis(user.getId());
        if (analysis.isEmpty()) {
            // Profile fallback analysis
            ResumeAnalysis created = resumeAnalysisService.analyzeProfileBased(user.getId());
            return ResponseEntity.ok(Map.of("success", true, "data", created));
        }
        return ResponseEntity.ok(Map.of("success", true, "data", analysis.get()));
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> triggerAnalysis(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        ResumeAnalysis analysis = resumeAnalysisService.analyzeProfileBased(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "message", "Analysis generated!", "data", analysis));
    }
}
