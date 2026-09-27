package com.quest.app.service;

import com.quest.app.model.*;
import com.quest.app.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseLessonRepository courseLessonRepository;

    @Autowired
    private CourseAssessmentRepository courseAssessmentRepository;

    @Autowired
    private CourseProgressRepository courseProgressRepository;

    @Autowired
    private CertificateService certificateService;

    @PostConstruct
    public void seedInitialCourses() {
        if (courseRepository.count() == 0) {
            createCourseWithContent(
                "Java Fundamentals", "Programming", 
                "Master core Java programming syntax, OOP concepts, collections, exception handling, and standard backend libraries.", 
                "6 Hours", "Beginner", "QUEST Academy", "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=600",
                List.of(
                    "Lesson 1: Java Syntax, Variables, Data Types & Operations",
                    "Lesson 2: Control Flow, Arrays, Methods & Object-Oriented Programming",
                    "Lesson 3: Collections Framework, Exception Handling & File I/O"
                ),
                List.of(
                    new CourseAssessment(null, "Which keyword is used to declare a class in Java?", "class", "define", "struct", "interface", "A"),
                    new CourseAssessment(null, "What is the entry point method signature for a standard Java program?", "public void start()", "public static void main(String[] args)", "static void run()", "public int main()", "B")
                )
            );

            createCourseWithContent(
                "Python for Data Analysis", "Data Science", 
                "Learn data manipulation, cleaning, visualization, and basic statistical analysis using Python, Pandas, NumPy, and Matplotlib.", 
                "8 Hours", "Intermediate", "Dr. Sarah Lin", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=600",
                List.of(
                    "Lesson 1: Python Data Structures & Control Logic",
                    "Lesson 2: Data Manipulation with Pandas & NumPy DataFrames",
                    "Lesson 3: Data Visualization with Matplotlib & Seaborn"
                ),
                List.of(
                    new CourseAssessment(null, "Which Pandas function is used to load a CSV file into a DataFrame?", "pd.read_csv()", "pd.load_csv()", "pd.import_file()", "pd.open_csv()", "A")
                )
            );

            createCourseWithContent(
                "SQL for Beginners", "Database", 
                "Learn relational database design, querying with SELECT, WHERE, JOINs, GROUP BY, and data manipulation language commands.", 
                "5 Hours", "Beginner", "QUEST Tech", "https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=600",
                List.of(
                    "Lesson 1: Database Concepts & Basic SELECT Queries",
                    "Lesson 2: Filtering Data with WHERE, ORDER BY & Aggregate Functions",
                    "Lesson 3: Multi-Table Joins & Grouping Data with GROUP BY"
                ),
                List.of(
                    new CourseAssessment(null, "Which clause is used to filter records in a SQL SELECT statement?", "WHERE", "GROUP BY", "HAVING", "LIMIT", "A")
                )
            );

            createCourseWithContent(
                "Communication Skills", "Soft Skills", 
                "Enhance professional communication, active listening, structured email writing, and persuasive presentation skills.", 
                "4 Hours", "All Levels", "QUEST Career Center", "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=600",
                List.of(
                    "Lesson 1: Principles of Clear & Empathetic Communication",
                    "Lesson 2: Professional Workplace Email & Document Writing",
                    "Lesson 3: Effective Public Speaking & Virtual Presentation"
                ),
                List.of(
                    new CourseAssessment(null, "What is an essential component of active listening?", "Interrupting quickly", "Paraphrasing & clarifying key points", "Formulating your response while they talk", "Checking your phone", "B")
                )
            );

            createCourseWithContent(
                "Data Analytics", "Data Science", 
                "Transform raw business data into actionable insights through Excel, SQL, Tableau, and business intelligence techniques.", 
                "7 Hours", "Intermediate", "QUEST Analytics", "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=600",
                List.of(
                    "Lesson 1: Data Analytics Lifecycle & KPI Definition",
                    "Lesson 2: Exploratory Data Analysis & Business Intelligence Metrics",
                    "Lesson 3: Building Interactive Dashboards & Executive Reports"
                ),
                List.of(
                    new CourseAssessment(null, "What does KPI stand for in business data analytics?", "Key Performance Indicator", "Known Program Index", "Key Process Integration", "Kernel Performance Log", "A")
                )
            );

            createCourseWithContent(
                "Web Development", "Software Engineering", 
                "Build modern, responsive full-stack websites using HTML5, CSS3, JavaScript, REST APIs, and backend frameworks.", 
                "10 Hours", "Beginner", "Alex Turner", "https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=600",
                List.of(
                    "Lesson 1: HTML5 Markup Structure & CSS3 Styling Fundamentals",
                    "Lesson 2: JavaScript DOM Manipulation & Dynamic Event Handling",
                    "Lesson 3: Connecting Web Frontends to Spring Boot REST APIs"
                ),
                List.of(
                    new CourseAssessment(null, "Which HTML tag is used to embed JavaScript code in a webpage?", "<script>", "<js>", "<javascript>", "<code>", "A")
                )
            );

            createCourseWithContent(
                "Cloud Fundamentals", "Cloud Computing", 
                "Understand cloud architecture, AWS / Azure services, IaaS / PaaS models, cloud security, and deployment strategies.", 
                "6 Hours", "Beginner", "QUEST Cloud Lab", "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=600",
                List.of(
                    "Lesson 1: Introduction to Cloud Infrastructure & Virtualization",
                    "Lesson 2: Cloud Storage, Compute Instances & Virtual Networks",
                    "Lesson 3: Cloud Security, IAM Policies & Deployment Models"
                ),
                List.of(
                    new CourseAssessment(null, "What type of cloud model is AWS EC2 considered?", "IaaS (Infrastructure as a Service)", "PaaS (Platform as a Service)", "SaaS (Software as a Service)", "FaaS (Function as a Service)", "A")
                )
            );

            createCourseWithContent(
                "Cybersecurity Basics", "Security", 
                "Learn core information security principles, threat mitigation, encryption, network security, and secure coding practices.", 
                "5 Hours", "Beginner", "QUEST Security Team", "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?w=600",
                List.of(
                    "Lesson 1: Information Security Fundamentals & CIA Triad",
                    "Lesson 2: Common Cyber Threats, Malware & Phishing Prevention",
                    "Lesson 3: Encryption Basics, Password Hashing & Safe Network Habits"
                ),
                List.of(
                    new CourseAssessment(null, "What does the 'C' stand for in the CIA Triad of security?", "Confidentiality", "Control", "Cipher", "Centralized", "A")
                )
            );

            createCourseWithContent(
                "Interview Preparation", "Career Skills", 
                "Prepare for technical screening, STAR method behavioral interviews, salary negotiation, and confident communication.", 
                "4 Hours", "All Levels", "QUEST Coaching", "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=600",
                List.of(
                    "Lesson 1: Structuring Responses with the STAR Method",
                    "Lesson 2: Tackling Technical & Problem-Solving Interview Questions",
                    "Lesson 3: Post-Interview Follow-Up & Salary Offer Negotiation"
                ),
                List.of(
                    new CourseAssessment(null, "What does STAR stand for in behavioral interviews?", "Situation, Task, Action, Result", "Status, Time, Answer, Review", "Strategy, Team, Analysis, Report", "Skill, Talent, Aptitude, Readiness", "A")
                )
            );

            createCourseWithContent(
                "Resume Building", "Career Skills", 
                "Craft an ATS-friendly, high-impact resume that highlights your achievements, technical projects, and core competencies.", 
                "3 Hours", "All Levels", "QUEST Career Services", "https://images.unsplash.com/photo-1586281380349-632531db7ed4?w=600",
                List.of(
                    "Lesson 1: Resume Layout, Formatting & ATS Optimization Principles",
                    "Lesson 2: Quantifying Achievements & Writing High-Impact Bullet Points",
                    "Lesson 3: Tailoring Your Resume for Specific Job Descriptions"
                ),
                List.of(
                    new CourseAssessment(null, "What is the primary goal of optimizing a resume for ATS?", "Ensuring automated applicant tracking systems parse your qualifications accurately", "Making it colorful with graphics", "Filing it with the government", "Limiting it to 5 pages", "A")
                )
            );
        }
    }

    private void createCourseWithContent(String title, String category, String description, String duration, String level, String instructor, String imageUrl, List<String> lessonTitles, List<CourseAssessment> assessments) {
        Course course = new Course(title, category, description, duration, level, instructor, imageUrl);
        course.setTotalLessons(lessonTitles.size());
        Course savedCourse = courseRepository.save(course);

        int order = 1;
        for (String lTitle : lessonTitles) {
            String body = "### " + lTitle + "\n\nWelcome to this lesson in **" + title + "**.\n\nIn this comprehensive module, we cover core principles, real-world examples, and step-by-step practical guides to build your proficiency. Review the concepts thoroughly before taking the end-of-course assessment.";
            CourseLesson lesson = new CourseLesson(savedCourse.getId(), order++, lTitle, body, 20);
            courseLessonRepository.save(lesson);
        }

        for (CourseAssessment assessment : assessments) {
            assessment.setCourseId(savedCourse.getId());
            courseAssessmentRepository.save(assessment);
        }
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<CourseLesson> getLessonsForCourse(Long courseId) {
        return courseLessonRepository.findByCourseIdOrderByLessonOrderAsc(courseId);
    }

    public List<CourseAssessment> getAssessmentsForCourse(Long courseId) {
        return courseAssessmentRepository.findByCourseId(courseId);
    }

    public CourseProgress getOrCreateProgress(Long userId, Long courseId) {
        return courseProgressRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseGet(() -> courseProgressRepository.save(new CourseProgress(userId, courseId)));
    }

    public CourseProgress completeLesson(Long userId, Long courseId, Integer lessonOrder) {
        CourseProgress progress = getOrCreateProgress(userId, courseId);
        if (progress.getCompletedLessons() < lessonOrder) {
            progress.setCompletedLessons(lessonOrder);
        }
        return courseProgressRepository.save(progress);
    }

    public Map<String, Object> submitAssessment(Long userId, Long courseId, List<String> userAnswers) {
        List<CourseAssessment> questions = getAssessmentsForCourse(courseId);
        int correctCount = 0;
        int total = questions.size();

        for (int i = 0; i < total && i < userAnswers.size(); i++) {
            if (questions.get(i).getCorrectOption().equalsIgnoreCase(userAnswers.get(i))) {
                correctCount++;
            }
        }

        int scorePercentage = total > 0 ? (int) Math.round(((double) correctCount / total) * 100) : 100;
        boolean passed = scorePercentage >= 60;

        CourseProgress progress = getOrCreateProgress(userId, courseId);
        progress.setAssessmentScore(scorePercentage);
        if (passed) {
            progress.setIsCompleted(true);
            progress.setCompletedDate(LocalDateTime.now());
            courseProgressRepository.save(progress);

            // Generate QUEST Course Completion Certificate
            Course course = courseRepository.findById(courseId).orElseThrow();
            certificateService.generateCertificate(userId, course.getId(), course.getTitle(), course.getCategory());
        }

        return Map.of(
            "score", scorePercentage,
            "passed", passed,
            "correctCount", correctCount,
            "total", total,
            "message", passed ? "Congratulations! Course Completed. Your QUEST Certificate has been issued!" : "Keep practicing! Score at least 60% to earn your certificate."
        );
    }
}
