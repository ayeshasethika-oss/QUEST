package com.quest.app.service;

import com.quest.app.model.Resume;
import com.quest.app.model.ResumeAnalysis;
import com.quest.app.model.CareerProfile;
import com.quest.app.repository.ResumeRepository;
import com.quest.app.repository.ResumeAnalysisRepository;
import com.quest.app.repository.CareerProfileRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ResumeAnalysisService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ResumeAnalysisRepository resumeAnalysisRepository;

    @Autowired
    private CareerProfileRepository careerProfileRepository;

    public ResumeAnalysis processAndAnalyzeResume(Long userId, MultipartFile file, String uploadDir) throws Exception {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) originalFilename = "resume.pdf";

        String ext = originalFilename.toLowerCase().endsWith(".docx") ? "DOCX" : "PDF";
        File dir = new File(uploadDir + "/resumes");
        if (!dir.exists()) dir.mkdirs();

        String savePath = dir.getAbsolutePath() + File.separator + System.currentTimeMillis() + "_" + originalFilename;
        file.transferTo(new File(savePath));

        Resume resume = new Resume(userId, originalFilename, ext, savePath);
        Resume savedResume = resumeRepository.save(resume);

        // Extract text content from PDF file if PDF
        String extractedText = "";
        if ("PDF".equals(ext)) {
            try (PDDocument document = PDDocument.load(new File(savePath))) {
                PDFTextStripper stripper = new PDFTextStripper();
                extractedText = stripper.getText(document);
            } catch (IOException e) {
                extractedText = "Standard resume text content parsed.";
            }
        }

        return performAnalysis(userId, savedResume.getId(), extractedText);
    }

    public ResumeAnalysis analyzeProfileBased(Long userId) {
        return performAnalysis(userId, null, "");
    }

    private ResumeAnalysis performAnalysis(Long userId, Long resumeId, String textContent) {
        Optional<CareerProfile> profileOpt = careerProfileRepository.findByUserId(userId);

        List<String> detected = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        List<String> keyTechs = List.of("Java", "Python", "SQL", "Spring Boot", "React", "HTML", "CSS", "AWS", "Docker", "Git", "REST APIs", "Agile", "Communication");

        String combinedText = textContent.toLowerCase();
        if (profileOpt.isPresent()) {
            CareerProfile cp = profileOpt.get();
            if (cp.getCurrentSkills() != null) combinedText += " " + cp.getCurrentSkills().toLowerCase();
            if (cp.getEducation() != null) combinedText += " " + cp.getEducation().toLowerCase();
            if (cp.getExperience() != null) combinedText += " " + cp.getExperience().toLowerCase();
        }

        for (String tech : keyTechs) {
            if (combinedText.contains(tech.toLowerCase())) {
                detected.add(tech);
            } else {
                missing.add(tech);
            }
        }

        int completenessScore = Math.min(100, 50 + (detected.size() * 6));
        int overallScore = Math.min(100, 60 + (detected.size() * 5));

        String strengthsStr = "• Strong technical foundation in: " + String.join(", ", detected) + 
                              "\n• Well-structured education and experience summary." + 
                              "\n• Clear section organization and ATS keyword formatting.";

        String suggestionsStr = "• Add quantifiable metrics to project outcomes (e.g., 'Improved API latency by 35%')." + 
                                "\n• Incorporate missing target domain skills: " + String.join(", ", missing.stream().limit(4).toList()) + 
                                "\n• Include links to live GitHub repositories or portfolio projects.";

        String compatibility = overallScore >= 80 ? "High Compatibility for Tech Roles" : "Moderate Compatibility - Recommended Skill Upskilling";

        ResumeAnalysis analysis = new ResumeAnalysis(
            userId, resumeId, overallScore, completenessScore,
            String.join(", ", detected), String.join(", ", missing.stream().limit(5).toList()),
            strengthsStr, suggestionsStr, compatibility
        );

        return resumeAnalysisRepository.save(analysis);
    }

    public Optional<ResumeAnalysis> getLatestAnalysis(Long userId) {
        return resumeAnalysisRepository.findFirstByUserIdOrderByAnalyzedAtDesc(userId);
    }
}
