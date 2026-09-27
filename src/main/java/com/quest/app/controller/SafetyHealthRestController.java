package com.quest.app.controller;

import com.quest.app.model.TrustedContact;
import com.quest.app.model.User;
import com.quest.app.service.SafetyHealthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/safety")
public class SafetyHealthRestController {

    @Autowired
    private SafetyHealthService safetyHealthService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @GetMapping("/trusted-contacts")
    public ResponseEntity<?> getTrustedContacts(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        return ResponseEntity.ok(Map.of("success", true, "data", safetyHealthService.getTrustedContacts(user.getId())));
    }

    @PostMapping("/trusted-contacts")
    public ResponseEntity<?> addTrustedContact(@RequestBody Map<String, String> body, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        String name = body.get("name");
        String relationship = body.get("relationship");
        String phone = body.get("phone");
        String email = body.get("email");

        TrustedContact contact = safetyHealthService.addTrustedContact(user.getId(), name, relationship, phone, email);
        return ResponseEntity.ok(Map.of("success", true, "message", "Trusted contact saved!", "data", contact));
    }

    @DeleteMapping("/trusted-contacts/{id}")
    public ResponseEntity<?> deleteTrustedContact(@PathVariable Long id, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        safetyHealthService.deleteTrustedContact(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Trusted contact removed."));
    }

    @GetMapping("/wellness")
    public ResponseEntity<?> getWellness(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        return ResponseEntity.ok(Map.of("success", true, "data", safetyHealthService.getGeneralWellnessRecommendations(user.getId())));
    }
}
