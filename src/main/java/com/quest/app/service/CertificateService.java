package com.quest.app.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.quest.app.model.Certificate;
import com.quest.app.model.User;
import com.quest.app.repository.CertificateRepository;
import com.quest.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${quest.upload.dir:C:/Users/ELCOT/.gemini/antigravity/scratch/QUEST/uploads}")
    private String uploadDir;

    public String generateCertificateId() {
        Random random = new Random();
        int num = 10000 + random.nextInt(90000);
        return "CERT-" + num;
    }

    public Certificate generateCertificate(Long userId, Long courseId, String courseTitle, String category) {
        Optional<Certificate> existing = certificateRepository.findByUserIdAndCourseId(userId, courseId);
        if (existing.isPresent()) {
            return existing.get();
        }

        User user = userRepository.findById(userId).orElseThrow();
        String certId = generateCertificateId();
        String fileName = certId + "_" + userId + ".pdf";
        File dir = new File(uploadDir + "/certificates");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String pdfFilePath = dir.getAbsolutePath() + File.separator + fileName;

        try {
            createPdfFile(pdfFilePath, user.getFullName(), user.getQuestProfileId(), courseTitle, category, certId, LocalDate.now().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Certificate cert = new Certificate(certId, userId, user.getFullName(), user.getQuestProfileId(), courseId, courseTitle, category, pdfFilePath);
        return certificateRepository.save(cert);
    }

    private void createPdfFile(String filePath, String fullName, String questProfileId, String courseTitle, String skills, String certId, String dateStr) throws Exception {
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 36, 36);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Draw Decorative Border
        PdfContentByte canvas = writer.getDirectContent();
        canvas.setColorStroke(new Color(219, 39, 119)); // Dark pink/violet accent
        canvas.setLineWidth(4f);
        canvas.rectangle(20, 20, PageSize.A4.getHeight() - 40, PageSize.A4.getWidth() - 40);
        canvas.stroke();

        canvas.setColorStroke(new Color(99, 102, 241)); // Indigo secondary accent
        canvas.setLineWidth(1.5f);
        canvas.rectangle(28, 28, PageSize.A4.getHeight() - 56, PageSize.A4.getWidth() - 56);
        canvas.stroke();

        // Title & Header Fonts
        Font brandFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 28, new Color(219, 39, 119));
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 14, new Color(148, 163, 184));
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, new Color(30, 41, 59));
        Font nameFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 26, new Color(15, 23, 42));
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 13, new Color(71, 85, 105));
        Font courseFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(99, 102, 241));
        Font metaFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, new Color(100, 116, 139));

        Paragraph pBrand = new Paragraph("🚀 QUEST", brandFont);
        pBrand.setAlignment(Element.ALIGN_CENTER);
        document.add(pBrand);

        Paragraph pTagline = new Paragraph("Your Journey Toward a Goal", subtitleFont);
        pTagline.setAlignment(Element.ALIGN_CENTER);
        document.add(pTagline);

        document.add(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 10)));

        Paragraph pCertTitle = new Paragraph("QUEST COURSE COMPLETION CERTIFICATE", titleFont);
        pCertTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(pCertTitle);

        document.add(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 15)));

        Paragraph pPresented = new Paragraph("This is to certify that", bodyFont);
        pPresented.setAlignment(Element.ALIGN_CENTER);
        document.add(pPresented);

        Paragraph pName = new Paragraph(fullName.toUpperCase(), nameFont);
        pName.setAlignment(Element.ALIGN_CENTER);
        document.add(pName);

        Paragraph pId = new Paragraph("QUEST Profile ID: " + questProfileId, metaFont);
        pId.setAlignment(Element.ALIGN_CENTER);
        document.add(pId);

        document.add(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 10)));

        Paragraph pDesc = new Paragraph("has successfully completed all coursework, practical modules, and required assessments for", bodyFont);
        pDesc.setAlignment(Element.ALIGN_CENTER);
        document.add(pDesc);

        Paragraph pCourse = new Paragraph(courseTitle, courseFont);
        pCourse.setAlignment(Element.ALIGN_CENTER);
        document.add(pCourse);

        Paragraph pSkills = new Paragraph("Core Domain Skills: " + skills, bodyFont);
        pSkills.setAlignment(Element.ALIGN_CENTER);
        document.add(pSkills);

        document.add(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 20)));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(90);

        PdfPCell cell1 = new PdfPCell(new Paragraph("Date of Completion: " + dateStr + "\nCertificate ID: " + certId, metaFont));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell cell2 = new PdfPCell(new Paragraph("Authorized by QUEST Learning Council\nVerification: quest.app/verify/" + certId, metaFont));
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(cell1);
        table.addCell(cell2);
        document.add(table);

        document.close();
    }

    public List<Certificate> getUserCertificates(Long userId) {
        return certificateRepository.findByUserId(userId);
    }

    public Optional<Certificate> getCertificateById(Long id) {
        return certificateRepository.findById(id);
    }
}
