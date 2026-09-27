package com.quest.app.service;

import com.quest.app.model.CareerProfile;
import com.quest.app.model.User;
import com.quest.app.repository.CareerProfileRepository;
import com.quest.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CareerService {

    @Autowired
    private CareerProfileRepository careerProfileRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> analyzeCareer(Long userId) {
        Map<String, Object> result = new HashMap<>();

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<CareerProfile> profileOpt = careerProfileRepository.findByUserId(userId);

        if (userOpt.isEmpty() || profileOpt.isEmpty()) {
            result.put("error", "Profile not complete");
            return result;
        }

        User user = userOpt.get();
        CareerProfile profile = profileOpt.get();

        String goal = profile.getCareerGoal() != null ? profile.getCareerGoal() : "Software Engineer / Career Advancement";
        String currentSkillsStr = profile.getCurrentSkills() != null ? profile.getCurrentSkills() : "";
        String interestedSkillsStr = profile.getInterestedSkills() != null ? profile.getInterestedSkills() : "";

        List<String> currentSkills = Arrays.stream(currentSkillsStr.split(","))
                .map(String::trim).filter(s -> !s.isEmpty()).toList();
        List<String> interestedSkills = Arrays.stream(interestedSkillsStr.split(","))
                .map(String::trim).filter(s -> !s.isEmpty()).toList();

        // Skill Gap calculation
        List<String> recommendedSkills = new ArrayList<>();
        if (goal.toLowerCase().contains("data") || goal.toLowerCase().contains("analytics")) {
            recommendedSkills.addAll(List.of("Python", "SQL", "PowerBI", "Pandas", "Machine Learning"));
        } else if (goal.toLowerCase().contains("web") || goal.toLowerCase().contains("frontend") || goal.toLowerCase().contains("fullstack")) {
            recommendedSkills.addAll(List.of("JavaScript", "React", "HTML5", "CSS3", "REST APIs", "Node.js"));
        } else if (goal.toLowerCase().contains("cloud") || goal.toLowerCase().contains("devops")) {
            recommendedSkills.addAll(List.of("AWS", "Docker", "Kubernetes", "Linux", "CI/CD"));
        } else {
            recommendedSkills.addAll(List.of("Java", "Spring Boot", "SQL", "Git", "Problem Solving", "Communication"));
        }

        List<String> missingSkills = new ArrayList<>();
        for (String rec : recommendedSkills) {
            boolean present = currentSkills.stream().anyMatch(cs -> cs.equalsIgnoreCase(rec));
            if (!present) {
                missingSkills.add(rec);
            }
        }

        int totalExpected = recommendedSkills.size();
        int matched = totalExpected - missingSkills.size();
        int suitabilityScore = totalExpected > 0 ? (int) Math.round(((double) matched / totalExpected) * 100) : 75;
        if (suitabilityScore < 40) suitabilityScore = 55;

        // Roadmap Steps
        List<Map<String, String>> roadmap = new ArrayList<>();
        roadmap.add(Map.of("step", "1", "title", "Assess Current Skills", "desc", "Verified " + currentSkills.size() + " active competencies in " + (profile.getPreferredCareerArea() != null ? profile.getPreferredCareerArea() : "your target domain")));
        roadmap.add(Map.of("step", "2", "title", "Identify Career Direction", "desc", "Target Goal: " + goal));
        roadmap.add(Map.of("step", "3", "title", "Bridge Priority Skill Gaps", "desc", "Focus learning on: " + String.join(", ", missingSkills)));
        roadmap.add(Map.of("step", "4", "title", "Master Priority Courses", "desc", "Complete recommended QUEST Skill Courses & earn verifiable certificates"));
        roadmap.add(Map.of("step", "5", "title", "Resume & Mock Interview Prep", "desc", "Upload updated resume for AI score analysis & complete role mock interview"));
        roadmap.add(Map.of("step", "6", "title", "Apply & Career Growth", "desc", "Discover & apply to tailored opportunities"));

        result.put("userName", user.getFullName());
        result.put("questProfileId", user.getQuestProfileId());
        result.put("careerGoal", goal);
        result.put("currentSkills", currentSkills);
        result.put("interestedSkills", interestedSkills);
        result.put("missingSkills", missingSkills);
        result.put("recommendedSkills", recommendedSkills);
        result.put("suitabilityScore", suitabilityScore);
        result.put("roadmap", roadmap);
        result.put("isCareerBreak", Boolean.TRUE.equals(profile.getCareerBreak()));
        result.put("careerBreakReason", profile.getCareerBreakReason());

        return result;
    }

    public Map<String, Object> analyzeCareerRestart(Long userId) {
        Map<String, Object> analysis = analyzeCareer(userId);

        Optional<CareerProfile> profileOpt = careerProfileRepository.findByUserId(userId);
        if (profileOpt.isPresent()) {
            CareerProfile cp = profileOpt.get();
            analysis.put("isCareerBreak", Boolean.TRUE.equals(cp.getCareerBreak()));
            analysis.put("breakReason", cp.getCareerBreakReason());
            analysis.put("refresherSkills", List.of("Industry Tools Refresher", "Agile Methodologies", "Modern Workflow Automation", "Interview Readiness"));
            analysis.put("jobReadinessScore", 82);
        }

        return analysis;
    }
}
