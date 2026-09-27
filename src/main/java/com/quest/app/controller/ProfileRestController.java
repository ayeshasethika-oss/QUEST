package com.quest.app.controller;

import com.quest.app.model.User;
import com.quest.app.model.CareerProfile;
import com.quest.app.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {

    @Autowired
    private ProfileService profileService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @GetMapping
    public ResponseEntity<?> getProfile(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));
        }
        CareerProfile profile = profileService.getCareerProfileByUserId(user.getId()).orElse(new CareerProfile(user.getId()));
        return ResponseEntity.ok(Map.of("success", true, "user", user, "profile", profile));
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> body, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));
        }

        String education = (String) body.get("education");
        String currentStatus = (String) body.get("currentStatus");
        String careerGoal = (String) body.get("careerGoal");
        String currentSkills = (String) body.get("currentSkills");
        String interestedSkills = (String) body.get("interestedSkills");
        String experience = (String) body.get("experience");
        String internships = (String) body.get("internships");
        String certifications = (String) body.get("certifications");
        String projects = (String) body.get("projects");
        Boolean careerBreak = Boolean.TRUE.equals(body.get("careerBreak"));
        String careerBreakReason = (String) body.get("careerBreakReason");
        String preferredCareerArea = (String) body.get("preferredCareerArea");

        CareerProfile updatedProfile = profileService.updateProfile(
            user.getId(), education, currentStatus, careerGoal, currentSkills,
            interestedSkills, experience, internships, certifications, projects,
            careerBreak, careerBreakReason, preferredCareerArea
        );

        if (body.containsKey("fullName") || body.containsKey("phone") || body.containsKey("age")) {
            String fullName = (String) body.get("fullName");
            Integer age = body.get("age") != null ? Integer.parseInt(body.get("age").toString()) : null;
            String phone = (String) body.get("phone");
            User updatedUser = profileService.updateUserInfo(user.getId(), fullName, age, phone, null);
            session.setAttribute("loggedInUser", updatedUser);
        }

        return ResponseEntity.ok(Map.of("success", true, "message", "Profile updated successfully!", "data", updatedProfile));
    }

    @PutMapping("/photo")
    public ResponseEntity<?> updatePhoto(@RequestBody Map<String, String> body, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        String photoPath = body.get("photoPath");
        if (photoPath == null || photoPath.trim().isEmpty()) photoPath = "default_avatar.png";

        User updatedUser = profileService.updateUserInfo(user.getId(), null, null, null, photoPath);
        session.setAttribute("loggedInUser", updatedUser);

        return ResponseEntity.ok(Map.of("success", true, "message", "Profile photo updated!", "photo", updatedUser.getProfilePhoto()));
    }
}
