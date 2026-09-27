package com.quest.app.controller;

import com.quest.app.model.Application;
import com.quest.app.model.Opportunity;
import com.quest.app.model.User;
import com.quest.app.service.OpportunityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityRestController {

    @Autowired
    private OpportunityService opportunityService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @GetMapping
    public ResponseEntity<?> getAllOpportunities() {
        return ResponseEntity.ok(Map.of("success", true, "data", opportunityService.getAllOpportunities()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOpportunityById(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("success", true, "data", opportunityService.getOpportunityById(id).orElseThrow()));
    }

    @GetMapping("/recommended")
    public ResponseEntity<?> getRecommended(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        List<Opportunity> recs = opportunityService.getRecommendedOpportunities(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "data", recs));
    }

    @PostMapping("/{id}/save")
    public ResponseEntity<?> toggleSave(@PathVariable Long id, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        boolean saved = opportunityService.saveOpportunity(user.getId(), id);
        return ResponseEntity.ok(Map.of("success", true, "saved", saved, "message", saved ? "Opportunity saved!" : "Opportunity unsaved!"));
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<?> apply(@PathVariable Long id, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        try {
            Application app = opportunityService.applyForOpportunity(user.getId(), id);
            return ResponseEntity.ok(Map.of("success", true, "message", "Application submitted successfully!", "data", app));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
