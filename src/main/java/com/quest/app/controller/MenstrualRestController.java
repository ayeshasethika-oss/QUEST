package com.quest.app.controller;

import com.quest.app.model.MenstrualRecord;
import com.quest.app.model.User;
import com.quest.app.service.MenstrualService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/menstrual")
public class MenstrualRestController {

    @Autowired
    private MenstrualService menstrualService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @PostMapping("/records")
    public ResponseEntity<?> logRecord(@RequestBody Map<String, Object> body, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        try {
            LocalDate startDate = LocalDate.parse((String) body.get("startDate"));
            Integer cycleLength = body.get("cycleLength") != null ? Integer.parseInt(body.get("cycleLength").toString()) : 28;
            Integer periodDuration = body.get("periodDuration") != null ? Integer.parseInt(body.get("periodDuration").toString()) : 5;
            String symptoms = (String) body.get("symptoms");
            String notes = (String) body.get("notes");

            MenstrualRecord record = menstrualService.logCycle(user.getId(), startDate, cycleLength, periodDuration, symptoms, notes);
            return ResponseEntity.ok(Map.of("success", true, "message", "Cycle record saved!", "data", record));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/records")
    public ResponseEntity<?> getRecords(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        try {
            return ResponseEntity.ok(Map.of("success", true, "data", menstrualService.getUserRecords(user.getId())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/insights")
    public ResponseEntity<?> getInsights(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        try {
            return ResponseEntity.ok(Map.of("success", true, "data", menstrualService.calculateCycleInsights(user.getId())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
