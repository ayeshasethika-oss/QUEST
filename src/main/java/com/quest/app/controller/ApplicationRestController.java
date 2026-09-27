package com.quest.app.controller;

import com.quest.app.model.Application;
import com.quest.app.model.User;
import com.quest.app.service.OpportunityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class ApplicationRestController {

    @Autowired
    private OpportunityService opportunityService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @GetMapping
    public ResponseEntity<?> getUserApplications(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        List<Application> apps = opportunityService.getUserApplications(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "data", apps));
    }
}
