# 🚀 QUEST — "Your Journey Toward a Goal"

A complete, modern, production-style, gender-inclusive career growth and personal development platform.

---

## 🎯 Platform Capabilities

- **Gender Inclusivity**: Male and Female users receive equal access to all core career growth features.
- **Female-Only Menstrual Health**: Period cycle tracking, period reminders, hygiene recommendations, and cycle predictions are strictly Female-only (completely hidden from Male users).
- **Safety First (Zero SOS)**: Safety features focus on Trusted Contacts management, Location Sharing tips, Safe Routes guidance, and Emergency Services directory. **No SOS button or SOS shortcut exists.**
- **Multi-Page Architecture**: Thymeleaf templates paired with REST APIs, Java Spring Boot, Spring Data JPA, and MySQL.
- **PDF Certificate Generation**: Downloadable PDF certificates generated via OpenPDF with unique Certificate IDs, QUEST Profile IDs, and course details.

---

## 🧑‍💻 Technical Requirements

1. **Java Development Kit (JDK)**: OpenJDK 17 or higher
2. **Build Tool**: Apache Maven 3.8+
3. **Database**: MySQL Server 8.0+ (Database name: `quest_db`)
4. **Development IDE**: VS Code / Antigravity IDE

---

## 🗄️ MySQL Database Setup

1. Start your local MySQL Server service.
2. Create the target database:
```sql
CREATE DATABASE quest_db;
```
3. Update database credentials in `src/main/resources/application.properties` (or set environment variables):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quest_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```
*Note: Hibernate `ddl-auto=update` will automatically generate all 22 required database tables on initial launch.*

---

## 🚀 How to Build & Run the Application

### Option A: Using Maven Command Line
```bash
cd C:\Users\ELCOT\.gemini\antigravity\scratch\QUEST
mvn clean package
mvn spring-boot:run
```

### Option B: Using Included PowerShell Launcher Script
```powershell
cd C:\Users\ELCOT\.gemini\antigravity\scratch\QUEST
powershell .\run-app.ps1
```

---

## 🌐 Application URL & Credentials

- **Main Application URL**: `http://localhost:8080`
- **Admin Portal URL**: `http://localhost:8080/admin/login`

### Demo Admin Credentials
- **Username**: `admin`
- **Password**: `admin123`

### Demo OTP Procedure
- During user registration or password reset, enter the safe demo OTP code: **`123456`**

---

## 📁 Multi-Page Structure Overview

```
QUEST/
├── pom.xml
├── run-app.ps1
├── README.md
└── src/
    └── main/
        ├── java/com/quest/app/
        │   ├── QuestApplication.java
        │   ├── config/
        │   ├── controller/
        │   ├── dto/
        │   ├── exception/
        │   ├── model/
        │   ├── repository/
        │   ├── service/
        │   └── util/
        └── resources/
            ├── templates/
            │   ├── login.html
            │   ├── register.html
            │   ├── otp.html
            │   ├── complete-profile.html
            │   ├── welcome.html
            │   ├── dashboard.html
            │   ├── profile.html
            │   ├── career.html
            │   ├── skills.html
            │   ├── courses.html
            │   ├── course-details.html
            │   ├── certificates.html
            │   ├── opportunities.html
            │   ├── opportunity-details.html
            │   ├── applications.html
            │   ├── resume.html
            │   ├── interview.html
            │   ├── safety-health.html
            │   ├── menstrual-health.html (Female-only)
            │   ├── notifications.html
            │   ├── settings.html
            │   └── admin/
            │       ├── login.html
            │       ├── dashboard.html
            │       ├── users.html
            │       ├── opportunities.html
            │       ├── courses.html
            │       ├── applications.html
            │       └── analytics.html
            └── static/
                ├── css/style.css
                ├── js/app.js
                └── images/
```

---

## 🏆 Summary of Features

1. **Registration & Auth**: Register -> Safe Demo OTP -> Profile Completion -> Personalized Welcome -> Dashboard.
2. **QUEST Profile ID**: Auto-generated unique ID (e.g. `QUEST-10482`) saved in MySQL.
3. **Career Analysis**: Predicts role suitability, skill gaps, personalized roadmap, industry trends.
4. **Skill Learning & Courses**: 10+ courses pre-seeded in MySQL with interactive assessments and lesson tracking.
5. **PDF Certificates**: Automated PDF certificate generation and download via OpenPDF.
6. **Career Restart**: Specialized learning path for professionals returning from a break.
7. **Resume Analysis**: Upload PDF/DOCX or analyze profile for ATS keyword density and scoring.
8. **AI Mock Interview**: Role selection, technical/HR questions, answer evaluation, custom Improvement Plan generator.
9. **Safety & Health**: Trusted Contacts CRUD, Safe Routes info, non-diagnostic general wellness tips (Hydration, sleep, posture). **No SOS feature**.
10. **Menstrual Health (Female Only)**: Period cycle tracking and hygiene reminders, strictly guarded against male access.
11. **Admin Portal**: Admin Dashboard metrics, User Management (without password exposure), Opportunity CRUD, Course CRUD, Application Monitoring, Analytics charts.
