package com.quest.app.controller;

import com.quest.app.model.Interview;
import com.quest.app.model.InterviewAnalysis;
import com.quest.app.model.InterviewQuestion;
import com.quest.app.model.User;
import com.quest.app.service.InterviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interview")
public class InterviewRestController {

    @Autowired
    private InterviewService interviewService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @PostMapping("/start")
    public ResponseEntity<?> startInterview(@RequestBody Map<String, String> body, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        String targetRole = body.getOrDefault("targetRole", "Software Engineer");
        Interview interview = interviewService.startInterview(user.getId(), targetRole);
        List<InterviewQuestion> questions = interviewService.getInterviewQuestions(interview.getId());

        return ResponseEntity.ok(Map.of("success", true, "interviewId", interview.getId(), "targetRole", targetRole, "questions", questions));
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<?> getQuestions(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("success", true, "questions", interviewService.getInterviewQuestions(id)));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitInterview(@PathVariable Long id, @RequestBody Map<String, Object> body, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        Map<String, String> rawAnswers = (Map<String, String>) body.get("answers");
        Map<Long, String> parsed = new HashMap<>();

        if (rawAnswers != null) {
            for (Map.Entry<String, String> e : rawAnswers.entrySet()) {
                parsed.put(Long.parseLong(e.getKey()), e.getValue());
            }
        }

        InterviewAnalysis analysis = interviewService.submitAnswersAndAnalyze(id, parsed);
        return ResponseEntity.ok(Map.of("success", true, "message", "Interview evaluated!", "data", analysis));
    }

    @GetMapping("/{id}/analysis")
    public ResponseEntity<?> getAnalysis(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("success", true, "data", interviewService.getAnalysisForInterview(id).orElseThrow()));
    }
}
