package com.quest.app.service;

import com.quest.app.model.*;
import com.quest.app.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private AuthService authService;

    @PostConstruct
    public void seedAdmin() {
        if (adminRepository.count() == 0) {
            String hashed = authService.hashPassword("admin123");
            Admin admin = new Admin("ADMIN-001", "admin", hashed, "System Administrator", "admin@quest.app");
            adminRepository.save(admin);
        }
    }

    public Admin loginAdmin(String username, String rawPassword) throws Exception {
        Optional<Admin> opt = adminRepository.findByUsername(username);
        if (opt.isEmpty()) {
            throw new Exception("Invalid Admin credentials!");
        }
        Admin admin = opt.get();
        if (!admin.getPassword().equals(authService.hashPassword(rawPassword))) {
            throw new Exception("Invalid Admin credentials!");
        }
        return admin;
    }

    public Map<String, Object> getAdminDashboardMetrics() {
        Map<String, Object> stats = new HashMap<>();

        long totalUsers = userRepository.count();
        long maleUsers = userRepository.countByGender("Male");
        long femaleUsers = userRepository.countByGender("Female");
        long totalOpportunities = opportunityRepository.count();
        long totalCourses = courseRepository.count();
        long totalApplications = applicationRepository.count();
        long totalCertificates = certificateRepository.count();
        long totalInterviews = interviewRepository.count();

        stats.put("totalUsers", totalUsers);
        stats.put("maleUsers", maleUsers);
        stats.put("femaleUsers", femaleUsers);
        stats.put("activeUsers", totalUsers);
        stats.put("totalOpportunities", totalOpportunities);
        stats.put("totalCourses", totalCourses);
        stats.put("totalApplications", totalApplications);
        stats.put("totalCertificates", totalCertificates);
        stats.put("totalInterviews", totalInterviews);

        return stats;
    }

    public List<User> getAllUsersSanitized() {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            u.setPassword("[PROTECTED_HASH]"); // Never expose password hashes
        }
        return users;
    }

    // Opportunity CRUD
    public Opportunity createOpportunity(Opportunity opp) {
        return opportunityRepository.save(opp);
    }

    public Opportunity updateOpportunity(Long id, Opportunity updated) {
        Opportunity existing = opportunityRepository.findById(id).orElseThrow();
        existing.setTitle(updated.getTitle());
        existing.setOrganization(updated.getOrganization());
        existing.setDescription(updated.getDescription());
        existing.setCategory(updated.getCategory());
        existing.setEligibility(updated.getEligibility());
        existing.setRequiredSkills(updated.getRequiredSkills());
        existing.setLocation(updated.getLocation());
        existing.setMode(updated.getMode());
        existing.setDeadline(updated.getDeadline());
        return opportunityRepository.save(existing);
    }

    public void deleteOpportunity(Long id) {
        opportunityRepository.deleteById(id);
    }

    // Course CRUD
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updated) {
        Course existing = courseRepository.findById(id).orElseThrow();
        existing.setTitle(updated.getTitle());
        existing.setCategory(updated.getCategory());
        existing.setDescription(updated.getDescription());
        existing.setDuration(updated.getDuration());
        existing.setLevel(updated.getLevel());
        existing.setInstructor(updated.getInstructor());
        existing.setImageUrl(updated.getImageUrl());
        return courseRepository.save(existing);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
