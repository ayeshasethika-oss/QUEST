package com.quest.app.service;

import com.quest.app.model.Notification;
import com.quest.app.model.NotificationPreference;
import com.quest.app.model.User;
import com.quest.app.repository.NotificationRepository;
import com.quest.app.repository.NotificationPreferenceRepository;
import com.quest.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationPreferenceRepository notificationPreferenceRepository;

    @Autowired
    private UserRepository userRepository;

    public Notification createNotification(Long userId, String title, String message, String category, Boolean isFemaleOnly) {
        Notification notification = new Notification(userId, title, message, category, isFemaleOnly);
        return notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Notification> all = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);

        // Enforce Male Menstrual Visibility Rule
        if ("Male".equalsIgnoreCase(user.getGender())) {
            return all.stream()
                    .filter(n -> !Boolean.TRUE.equals(n.getIsFemaleOnly()) && !"Menstrual".equalsIgnoreCase(n.getCategory()))
                    .toList();
        }
        return all;
    }

    public void markAsRead(Long notificationId) {
        Optional<Notification> opt = notificationRepository.findById(notificationId);
        if (opt.isPresent()) {
            Notification n = opt.get();
            n.setIsRead(true);
            notificationRepository.save(n);
        }
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public NotificationPreference getPreferences(Long userId) {
        return notificationPreferenceRepository.findByUserId(userId)
                .orElseGet(() -> notificationPreferenceRepository.save(new NotificationPreference(userId)));
    }

    public NotificationPreference updatePreferences(Long userId, Boolean opportunityAlerts, Boolean learningReminders, Boolean healthReminders, Boolean menstrualReminders) {
        NotificationPreference pref = getPreferences(userId);
        pref.setOpportunityAlerts(opportunityAlerts != null ? opportunityAlerts : true);
        pref.setLearningReminders(learningReminders != null ? learningReminders : true);
        pref.setHealthReminders(healthReminders != null ? healthReminders : true);
        pref.setMenstrualReminders(menstrualReminders != null ? menstrualReminders : true);
        return notificationPreferenceRepository.save(pref);
    }
}
