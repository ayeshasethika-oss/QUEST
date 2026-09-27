package com.quest.app.controller;

import com.quest.app.model.Admin;
import com.quest.app.model.Course;
import com.quest.app.model.Opportunity;
import com.quest.app.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> body, HttpSession session) {
        try {
            String username = body.get("username");
            String password = body.get("password");

            Admin admin = adminService.loginAdmin(username, password);
            session.setAttribute("adminUser", admin);

            return ResponseEntity.ok(Map.of("success", true, "message", "Admin authentication successful!", "data", admin));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(HttpSession session) {
        if (session.getAttribute("adminUser") == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Admin Unauthorized"));
        return ResponseEntity.ok(Map.of("success", true, "data", adminService.getAllUsersSanitized()));
    }

    @GetMapping("/analytics")
    public ResponseEntity<?> getAnalytics(HttpSession session) {
        if (session.getAttribute("adminUser") == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Admin Unauthorized"));
        return ResponseEntity.ok(Map.of("success", true, "data", adminService.getAdminDashboardMetrics()));
    }

    // Opportunity CRUD
    @PostMapping("/opportunities")
    public ResponseEntity<?> createOpportunity(@RequestBody Opportunity opp, HttpSession session) {
        if (session.getAttribute("adminUser") == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Admin Unauthorized"));
        return ResponseEntity.ok(Map.of("success", true, "data", adminService.createOpportunity(opp)));
    }

    @PutMapping("/opportunities/{id}")
    public ResponseEntity<?> updateOpportunity(@PathVariable Long id, @RequestBody Opportunity opp, HttpSession session) {
        if (session.getAttribute("adminUser") == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Admin Unauthorized"));
        return ResponseEntity.ok(Map.of("success", true, "data", adminService.updateOpportunity(id, opp)));
    }

    @DeleteMapping("/opportunities/{id}")
    public ResponseEntity<?> deleteOpportunity(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminUser") == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Admin Unauthorized"));
        adminService.deleteOpportunity(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Opportunity deleted successfully."));
    }

    // Course CRUD
    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody Course course, HttpSession session) {
        if (session.getAttribute("adminUser") == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Admin Unauthorized"));
        return ResponseEntity.ok(Map.of("success", true, "data", adminService.createCourse(course)));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course course, HttpSession session) {
        if (session.getAttribute("adminUser") == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Admin Unauthorized"));
        return ResponseEntity.ok(Map.of("success", true, "data", adminService.updateCourse(id, course)));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminUser") == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Admin Unauthorized"));
        adminService.deleteCourse(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Course deleted successfully."));
    }

    @GetMapping("/applications")
    public ResponseEntity<?> getApplications(HttpSession session) {
        if (session.getAttribute("adminUser") == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Admin Unauthorized"));
        return ResponseEntity.ok(Map.of("success", true, "data", adminService.getAllApplications()));
    }
}
