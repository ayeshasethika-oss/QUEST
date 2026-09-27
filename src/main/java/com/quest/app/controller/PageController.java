package com.quest.app.controller;

import com.quest.app.model.User;
import com.quest.app.model.CareerProfile;
import com.quest.app.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SafetyHealthService safetyHealthService;

    @Autowired
    private MenstrualService menstrualService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    // Root -> Login page
    @GetMapping("/")
    public String index(HttpSession session) {
        if (getLoggedInUser(session) != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (getLoggedInUser(session) != null) return "redirect:/dashboard";
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/otp")
    public String otpPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        model.addAttribute("demoOtp", "123456");
        return "otp";
    }

    @GetMapping("/complete-profile")
    public String completeProfilePage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        model.addAttribute("profile", profileService.getCareerProfileByUserId(user.getId()).orElse(new CareerProfile(user.getId())));
        return "complete-profile";
    }

    @GetMapping("/welcome")
    public String welcomePage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        model.addAttribute("profile", profileService.getCareerProfileByUserId(user.getId()).orElse(new CareerProfile(user.getId())));
        return "welcome";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("profile", profileService.getCareerProfileByUserId(user.getId()).orElse(new CareerProfile(user.getId())));
        model.addAttribute("savedOpportunitiesCount", opportunityService.getSavedOpportunities(user.getId()).size());
        model.addAttribute("applicationsCount", opportunityService.getUserApplications(user.getId()).size());
        model.addAttribute("certificatesCount", certificateService.getUserCertificates(user.getId()).size());
        model.addAttribute("notifications", notificationService.getUserNotifications(user.getId()));
        
        return "dashboard";
    }

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("profile", profileService.getCareerProfileByUserId(user.getId()).orElse(new CareerProfile(user.getId())));
        return "profile";
    }

    @GetMapping("/career")
    public String careerPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        return "career";
    }

    @GetMapping("/skills")
    public String skillsPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        return "skills";
    }

    @GetMapping("/courses")
    public String coursesPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses";
    }

    @GetMapping("/course-details/{id}")
    public String courseDetailsPage(@PathVariable Long id, HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("course", courseService.getCourseById(id).orElseThrow());
        model.addAttribute("lessons", courseService.getLessonsForCourse(id));
        model.addAttribute("assessments", courseService.getAssessmentsForCourse(id));
        model.addAttribute("progress", courseService.getOrCreateProgress(user.getId(), id));
        return "course-details";
    }

    @GetMapping("/certificates")
    public String certificatesPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("certificates", certificateService.getUserCertificates(user.getId()));
        return "certificates";
    }

    @GetMapping("/opportunities")
    public String opportunitiesPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("opportunities", opportunityService.getAllOpportunities());
        model.addAttribute("recommended", opportunityService.getRecommendedOpportunities(user.getId()));
        return "opportunities";
    }

    @GetMapping("/opportunity-details/{id}")
    public String opportunityDetailsPage(@PathVariable Long id, HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("opp", opportunityService.getOpportunityById(id).orElseThrow());
        return "opportunity-details";
    }

    @GetMapping("/applications")
    public String applicationsPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("applications", opportunityService.getUserApplications(user.getId()));
        return "applications";
    }

    @GetMapping("/resume")
    public String resumePage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        return "resume";
    }

    @GetMapping("/interview")
    public String interviewPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        return "interview";
    }

    @GetMapping("/safety-health")
    public String safetyHealthPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("trustedContacts", safetyHealthService.getTrustedContacts(user.getId()));
        model.addAttribute("wellness", safetyHealthService.getGeneralWellnessRecommendations(user.getId()));
        model.addAttribute("safety", safetyHealthService.getSafetyInformation());
        return "safety-health";
    }

    @GetMapping("/menstrual-health")
    public String menstrualHealthPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        // Enforce Male Menstrual Visibility Rule
        if (!"Female".equalsIgnoreCase(user.getGender())) {
            return "redirect:/dashboard"; // Male users redirected
        }

        model.addAttribute("user", user);
        try {
            model.addAttribute("insights", menstrualService.calculateCycleInsights(user.getId()));
            model.addAttribute("records", menstrualService.getUserRecords(user.getId()));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "menstrual-health";
    }

    @GetMapping("/notifications")
    public String notificationsPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("notifications", notificationService.getUserNotifications(user.getId()));
        model.addAttribute("preferences", notificationService.getPreferences(user.getId()));
        return "notifications";
    }

    @GetMapping("/settings")
    public String settingsPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        return "settings";
    }

    // Admin Views
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboardPage(HttpSession session, Model model) {
        if (session.getAttribute("adminUser") == null) return "redirect:/admin/login";
        return "admin/dashboard";
    }

    @GetMapping("/admin/users")
    public String adminUsersPage(HttpSession session, Model model) {
        if (session.getAttribute("adminUser") == null) return "redirect:/admin/login";
        return "admin/users";
    }

    @GetMapping("/admin/opportunities")
    public String adminOpportunitiesPage(HttpSession session, Model model) {
        if (session.getAttribute("adminUser") == null) return "redirect:/admin/login";
        return "admin/opportunities";
    }

    @GetMapping("/admin/courses")
    public String adminCoursesPage(HttpSession session, Model model) {
        if (session.getAttribute("adminUser") == null) return "redirect:/admin/login";
        return "admin/courses";
    }

    @GetMapping("/admin/applications")
    public String adminApplicationsPage(HttpSession session, Model model) {
        if (session.getAttribute("adminUser") == null) return "redirect:/admin/login";
        return "admin/applications";
    }

    @GetMapping("/admin/analytics")
    public String adminAnalyticsPage(HttpSession session, Model model) {
        if (session.getAttribute("adminUser") == null) return "redirect:/admin/login";
        return "admin/analytics";
    }
}
