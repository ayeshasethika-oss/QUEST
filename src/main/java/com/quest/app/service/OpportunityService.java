package com.quest.app.service;

import com.quest.app.model.*;
import com.quest.app.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OpportunityService {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private SavedOpportunityRepository savedOpportunityRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CareerProfileRepository careerProfileRepository;

    @PostConstruct
    public void seedInitialOpportunities() {
        if (opportunityRepository.count() == 0) {
            opportunityRepository.save(new Opportunity(
                "Junior Full-Stack Java Developer", "Nexus Software Systems",
                "Join our dynamic engineering team building high-performance cloud applications using Java, Spring Boot, React, and MySQL. Excellent mentorship and growth opportunities.",
                "Job", "B.Tech / B.E. / MCA in Computer Science or related fields", "Java, Spring Boot, MySQL, REST APIs, HTML/CSS",
                "Bangalore, India", "Hybrid", LocalDate.now().plusDays(30)
            ));

            opportunityRepository.save(new Opportunity(
                "Data Analytics Internship 2026", "Aether Analytics Labs",
                "Gain hands-on industry experience building data dashboards, automated SQL reports, and predictive models with Python and Pandas.",
                "Internship", "Students or fresh graduates with strong analytical background", "Python, SQL, Excel, Data Visualization",
                "Remote", "Remote", LocalDate.now().plusDays(20)
            ));

            opportunityRepository.save(new Opportunity(
                "National AI & Cloud Innovation Hackathon", "TechQuest Foundation",
                "Compete with top developer talent across the country to design innovative AI-powered solutions for career growth and community empowerment. Win cash prizes up to $10,000!",
                "Competition", "Open to all students, developers, and tech enthusiasts", "Python, Machine Learning, Cloud Architecture, Problem Solving",
                "Online", "Remote", LocalDate.now().plusDays(15)
            ));

            opportunityRepository.save(new Opportunity(
                "Women & Diversity in Tech STEM Scholarship", "Global Talent Inclusion Initiative",
                "Full tuition grant for specialized certifications in Cloud Engineering, Data Science, or Cybersecurity, accompanied by 1-on-1 executive mentorship.",
                "Scholarship", "Female students or women returning to tech careers after a break", "Enthusiasm for Technology, Basic Programming Knowledge",
                "Global / Online", "Remote", LocalDate.now().plusDays(45)
            ));

            opportunityRepository.save(new Opportunity(
                "Mega Software Engineering Recruitment Drive", "Apex Cloud Solutions",
                "Mass hiring drive for Associate Software Engineers, Quality Engineers, and Cloud Support Engineers across multiple locations.",
                "Recruitment Drive", "Graduates of 2024, 2025, or 2026", "Core Java, C++, Python, Data Structures, SQL",
                "Hyderabad, India", "On-site", LocalDate.now().plusDays(25)
            ));

            opportunityRepository.save(new Opportunity(
                "Career Restart Intensive Upskilling Program", "ReStart Leadership Hub",
                "A structured 8-week boot camp designed to refresh skills, update resumes, provide mock interview coaching, and directly connect professionals to hiring partners after a career break.",
                "Training Program", "Open to professionals returning from a career break of any duration", "Adaptability, Baseline Technical Knowledge, Effective Communication",
                "Mumbai, India", "Hybrid", LocalDate.now().plusDays(35)
            ));

            opportunityRepository.save(new Opportunity(
                "Cybersecurity Junior Analyst", "Fortress Security Systems",
                "Monitor network security logs, perform risk assessments, assist in incident response, and ensure compliance across corporate infrastructure.",
                "Job", "Degree in IT/Cybersecurity or Security Certification holders", "Linux, Networking, Information Security, Wireshark",
                "Chennai, India", "On-site", LocalDate.now().plusDays(40)
            ));

            opportunityRepository.save(new Opportunity(
                "Frontend Web Developer (React / Next.js)", "Vivid Interactive",
                "Craft slick, accessible, interactive web applications for high-growth tech startups. Requires strong mastery of UI/UX design systems.",
                "Job", "1-2 years experience or strong portfolio projects", "JavaScript, React, CSS3, UI/UX Design Systems, HTML5",
                "Remote", "Remote", LocalDate.now().plusDays(28)
            ));
        }
    }

    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    public Optional<Opportunity> getOpportunityById(Long id) {
        return opportunityRepository.findById(id);
    }

    public List<Opportunity> getRecommendedOpportunities(Long userId) {
        Optional<CareerProfile> profileOpt = careerProfileRepository.findByUserId(userId);
        if (profileOpt.isEmpty()) {
            return getAllOpportunities();
        }

        CareerProfile profile = profileOpt.get();
        String goal = profile.getCareerGoal() != null ? profile.getCareerGoal().toLowerCase() : "";
        List<Opportunity> all = getAllOpportunities();
        List<Opportunity> matched = new ArrayList<>();

        for (Opportunity opp : all) {
            if (opp.getTitle().toLowerCase().contains(goal) || 
                opp.getCategory().equalsIgnoreCase("Scholarship") || 
                (Boolean.TRUE.equals(profile.getCareerBreak()) && opp.getTitle().toLowerCase().contains("restart"))) {
                matched.add(opp);
            }
        }

        if (matched.isEmpty()) {
            return all.stream().limit(5).toList();
        }
        return matched;
    }

    public boolean saveOpportunity(Long userId, Long opportunityId) {
        Optional<SavedOpportunity> existing = savedOpportunityRepository.findByUserIdAndOpportunityId(userId, opportunityId);
        if (existing.isPresent()) {
            savedOpportunityRepository.delete(existing.get());
            return false; // unsaved
        } else {
            savedOpportunityRepository.save(new SavedOpportunity(userId, opportunityId));
            return true; // saved
        }
    }

    public List<Opportunity> getSavedOpportunities(Long userId) {
        List<SavedOpportunity> savedList = savedOpportunityRepository.findByUserId(userId);
        List<Long> ids = savedList.stream().map(SavedOpportunity::getOpportunityId).toList();
        return opportunityRepository.findAllById(ids);
    }

    public Application applyForOpportunity(Long userId, Long opportunityId) throws Exception {
        Optional<Application> existing = applicationRepository.findByUserIdAndOpportunityId(userId, opportunityId);
        if (existing.isPresent()) {
            throw new Exception("You have already applied for this opportunity!");
        }

        Opportunity opp = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new Exception("Opportunity not found"));

        Application app = new Application(userId, opportunityId, opp.getTitle(), opp.getOrganization(), opp.getDeadline());
        return applicationRepository.save(app);
    }

    public List<Application> getUserApplications(Long userId) {
        return applicationRepository.findByUserId(userId);
    }
}
