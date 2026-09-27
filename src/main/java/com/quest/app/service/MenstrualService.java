package com.quest.app.service;

import com.quest.app.model.MenstrualRecord;
import com.quest.app.model.User;
import com.quest.app.repository.MenstrualRecordRepository;
import com.quest.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MenstrualService {

    @Autowired
    private MenstrualRecordRepository menstrualRecordRepository;

    @Autowired
    private UserRepository userRepository;

    // Enforce Female-Only Access Rule
    public void validateFemaleAccess(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        if (!"Female".equalsIgnoreCase(user.getGender())) {
            throw new Exception("Access Denied: Menstrual Health features are available exclusively to female users.");
        }
    }

    public MenstrualRecord logCycle(Long userId, LocalDate startDate, Integer cycleLength, Integer periodDuration, String symptoms, String notes) throws Exception {
        validateFemaleAccess(userId);
        MenstrualRecord record = new MenstrualRecord(userId, startDate, cycleLength, periodDuration, symptoms, notes);
        return menstrualRecordRepository.save(record);
    }

    public List<MenstrualRecord> getUserRecords(Long userId) throws Exception {
        validateFemaleAccess(userId);
        return menstrualRecordRepository.findByUserIdOrderByStartDateDesc(userId);
    }

    public Map<String, Object> calculateCycleInsights(Long userId) throws Exception {
        validateFemaleAccess(userId);

        Optional<MenstrualRecord> latestOpt = menstrualRecordRepository.findFirstByUserIdOrderByStartDateDesc(userId);
        Map<String, Object> insights = new HashMap<>();

        if (latestOpt.isEmpty()) {
            insights.put("hasData", false);
            insights.put("message", "No cycle records logged yet. Start tracking your period for personalized reminders.");
            return insights;
        }

        MenstrualRecord record = latestOpt.get();
        LocalDate lastStart = record.getStartDate();
        int cycleDays = record.getCycleLengthDays() != null ? record.getCycleLengthDays() : 28;
        int periodDays = record.getPeriodDurationDays() != null ? record.getPeriodDurationDays() : 5;

        LocalDate nextPredictedStart = lastStart.plusDays(cycleDays);
        LocalDate estimatedOvulation = lastStart.plusDays(cycleDays - 14);

        long daysUntilNext = LocalDate.now().until(nextPredictedStart).getDays();

        insights.put("hasData", true);
        insights.put("lastStartDate", lastStart);
        insights.put("cycleLengthDays", cycleDays);
        insights.put("periodDurationDays", periodDays);
        insights.put("nextPredictedStart", nextPredictedStart);
        insights.put("estimatedOvulation", estimatedOvulation);
        insights.put("daysUntilNext", daysUntilNext);
        insights.put("hygieneTip", "Change hygiene products every 4-6 hours to maintain personal wellness and prevent irritation.");
        insights.put("careSuggestions", List.of(
            "Stay well hydrated with warm fluids or herbal teas.",
            "Incorporate gentle stretching or restorative yoga.",
            "Ensure adequate rest and iron-rich balanced meals."
        ));

        return insights;
    }
}
