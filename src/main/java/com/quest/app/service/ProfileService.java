package com.quest.app.service;

import com.quest.app.model.User;
import com.quest.app.model.CareerProfile;
import com.quest.app.repository.UserRepository;
import com.quest.app.repository.CareerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CareerProfileRepository careerProfileRepository;

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<CareerProfile> getCareerProfileByUserId(Long userId) {
        return careerProfileRepository.findByUserId(userId);
    }

    public CareerProfile updateProfile(Long userId, String education, String currentStatus, String careerGoal,
                                        String currentSkills, String interestedSkills, String experience,
                                        String internships, String certifications, String projects,
                                        Boolean careerBreak, String careerBreakReason, String preferredCareerArea) {
        
        CareerProfile profile = careerProfileRepository.findByUserId(userId)
                .orElse(new CareerProfile(userId));

        profile.setEducation(education);
        profile.setCurrentStatus(currentStatus);
        profile.setCareerGoal(careerGoal);
        profile.setCurrentSkills(currentSkills);
        profile.setInterestedSkills(interestedSkills);
        profile.setExperience(experience);
        profile.setInternships(internships);
        profile.setCertifications(certifications);
        profile.setProjects(projects);
        profile.setCareerBreak(careerBreak);
        profile.setCareerBreakReason(careerBreakReason);
        profile.setPreferredCareerArea(preferredCareerArea);
        profile.setUpdatedAt(LocalDateTime.now());

        return careerProfileRepository.save(profile);
    }

    public User updateUserInfo(Long userId, String fullName, Integer age, String phone, String photoPath) {
        User user = userRepository.findById(userId).orElseThrow();
        if (fullName != null && !fullName.trim().isEmpty()) user.setFullName(fullName);
        if (age != null) user.setAge(age);
        if (phone != null) user.setPhone(phone);
        if (photoPath != null) user.setProfilePhoto(photoPath);
        return userRepository.save(user);
    }
}
