package com.quest.app.service;

import com.quest.app.model.User;
import com.quest.app.model.CareerProfile;
import com.quest.app.model.NotificationPreference;
import com.quest.app.repository.UserRepository;
import com.quest.app.repository.CareerProfileRepository;
import com.quest.app.repository.NotificationPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CareerProfileRepository careerProfileRepository;

    @Autowired
    private NotificationPreferenceRepository notificationPreferenceRepository;

    // Generate Quest Profile ID (e.g., QUEST-10482)
    public String generateQuestProfileId() {
        Random random = new Random();
        int num = 10000 + random.nextInt(90000);
        String profileId = "QUEST-" + num;
        while (userRepository.findByQuestProfileId(profileId).isPresent()) {
            num = 10000 + random.nextInt(90000);
            profileId = "QUEST-" + num;
        }
        return profileId;
    }

    // Secure Hashing (SHA-256) for Passwords
    public String hashPassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return rawPassword;
        }
    }

    public User registerUser(String fullName, Integer age, String gender, String phone, String email, String password, String profilePhoto) throws Exception {
        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email address is already registered!");
        }

        String questId = generateQuestProfileId();
        String hashedPassword = hashPassword(password);
        String photo = (profilePhoto == null || profilePhoto.trim().isEmpty()) ? "default_avatar.png" : profilePhoto;

        User user = new User(fullName, age, gender, phone, email, hashedPassword, photo, questId);
        User savedUser = userRepository.save(user);

        // Initialize CareerProfile and NotificationPreference
        CareerProfile profile = new CareerProfile(savedUser.getId());
        careerProfileRepository.save(profile);

        NotificationPreference pref = new NotificationPreference(savedUser.getId());
        notificationPreferenceRepository.save(pref);

        return savedUser;
    }

    public User loginUser(String email, String rawPassword) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new Exception("Invalid email or password!");
        }

        User user = userOpt.get();
        String hashedInput = hashPassword(rawPassword);
        if (!user.getPassword().equals(hashedInput)) {
            throw new Exception("Invalid email or password!");
        }

        return user;
    }

    public void resetPassword(String email, String newPassword) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new Exception("No account found with this email!");
        }
        User user = userOpt.get();
        user.setPassword(hashPassword(newPassword));
        userRepository.save(user);
    }
}
