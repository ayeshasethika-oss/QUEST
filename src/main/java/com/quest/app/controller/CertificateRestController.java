package com.quest.app.controller;

import com.quest.app.model.Certificate;
import com.quest.app.model.User;
import com.quest.app.service.CertificateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/certificates")
public class CertificateRestController {

    @Autowired
    private CertificateService certificateService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @GetMapping
    public ResponseEntity<?> getUserCertificates(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        List<Certificate> certs = certificateService.getUserCertificates(user.getId());
        return ResponseEntity.ok(Map.of("success", true, "data", certs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCertificateById(@PathVariable Long id, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));

        Certificate cert = certificateService.getCertificateById(id).orElseThrow();
        return ResponseEntity.ok(Map.of("success", true, "data", cert));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable Long id, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return ResponseEntity.status(401).build();

        Certificate cert = certificateService.getCertificateById(id).orElseThrow();
        File file = new File(cert.getPdfPath());

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cert.getCertificateId() + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
