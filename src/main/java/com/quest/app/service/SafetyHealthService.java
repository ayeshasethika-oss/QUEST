package com.quest.app.service;

import com.quest.app.model.TrustedContact;
import com.quest.app.model.HealthRecord;
import com.quest.app.repository.TrustedContactRepository;
import com.quest.app.repository.HealthRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SafetyHealthService {

    @Autowired
    private TrustedContactRepository trustedContactRepository;

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    // Trusted Contacts CRUD
    public List<TrustedContact> getTrustedContacts(Long userId) {
        return trustedContactRepository.findByUserId(userId);
    }

    public TrustedContact addTrustedContact(Long userId, String name, String relationship, String phone, String email) {
        TrustedContact contact = new TrustedContact(userId, name, relationship, phone, email);
        return trustedContactRepository.save(contact);
    }

    public void deleteTrustedContact(Long contactId) {
        trustedContactRepository.deleteById(contactId);
    }

    // Health Log
    public HealthRecord logHealthActivity(Long userId, Integer hydrationGlasses, Double sleepHours, Integer activityMinutes, String stressLevel, String notes) {
        HealthRecord record = new HealthRecord(userId, hydrationGlasses, sleepHours, activityMinutes, stressLevel, notes);
        return healthRecordRepository.save(record);
    }

    public Optional<HealthRecord> getLatestHealthRecord(Long userId) {
        return healthRecordRepository.findFirstByUserIdOrderByRecordDateDesc(userId);
    }

    // General Non-Diagnostic Health & Wellness Recommendations
    public Map<String, Object> getGeneralWellnessRecommendations(Long userId) {
        Map<String, Object> wellness = new HashMap<>();

        wellness.put("hydrationTip", "Target 8-10 glasses (approx. 2.5 Liters) of water daily. Keep a water bottle at your workspace.");
        wellness.put("sleepGuidance", "Aim for 7-8 hours of uninterrupted rest to support cognitive focus and memory consolidation.");
        wellness.put("physicalActivity", "Incorporate 30 minutes of light walking, stretching, or movement breaks during study or work sessions.");
        wellness.put("stressManagement", "Practice deep breathing or mindfulness techniques when preparing for high-intensity interviews or exams.");
        wellness.put("screenTimeReminder", "Follow the 20-20-20 rule: Every 20 minutes, look at an object 20 feet away for at least 20 seconds.");
        wellness.put("postureGuidance", "Maintain ergonomic seating posture with screen at eye level and back supported.");
        wellness.put("workLifeBalance", "Schedule fixed study/work blocks with dedicated downtime to prevent burnout.");

        return wellness;
    }

    // Safety Information (NO SOS)
    public Map<String, Object> getSafetyInformation() {
        Map<String, Object> safety = new HashMap<>();

        safety.put("safeRoutesInfo", "Always select well-lit, populated routes during evening travel. Use live location sharing with trusted contacts.");
        safety.put("emergencyContacts", List.of(
            Map.of("service", "National Emergency Number", "number", "112"),
            Map.of("service", "Police Helpline", "number", "100"),
            Map.of("service", "Women Helpline", "number", "1091"),
            Map.of("service", "Medical Ambulance", "number", "108")
        ));
        safety.put("locationSharingTip", "You can share your live location or trip updates directly with your registered trusted contacts.");
        safety.put("personalSafetyGuidance", List.of(
            "Keep emergency contact numbers saved on quick dial.",
            "Inform trusted family or friends when commuting late or taking new routes.",
            "Maintain device battery above 20% when outside.",
            "Be vigilant of surrounding environments when using headphones in public."
        ));

        return safety;
    }
}
