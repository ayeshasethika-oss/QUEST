package com.quest.app.controller;

import com.quest.app.model.Notification;
import com.quest.app.model.NotificationPreference;
import com.quest.app.model.User;
import com.quest.app.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @GetMapping
    public ResponseEntity<?> getNotifications(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        List<Notification> list = notificationService.getUserNotifications(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "data", list));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Notification marked as read."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Notification deleted."));
    }

    @GetMapping("/preferences")
    public ResponseEntity<?> getPreferences(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        NotificationPreference pref = notificationService.getPreferences(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "data", pref));
    }

    @PutMapping("/preferences")
    public ResponseEntity<?> updatePreferences(@RequestBody Map<String, Boolean> body, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        NotificationPreference pref = notificationService.updatePreferences(
            user.getId(),
            body.get("opportunityAlerts"),
            body.get("learningReminders"),
            body.get("healthReminders"),
            body.get("menstrualReminders")
        );
        return ResponseEntity.ok(Map.of("success", true, "message", "Notification preferences saved!", "data", pref));
    }
}
