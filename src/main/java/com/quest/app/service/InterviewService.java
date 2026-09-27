package com.quest.app.service;

import com.quest.app.model.*;
import com.quest.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository;

    @Autowired
    private InterviewAnswerRepository interviewAnswerRepository;

    @Autowired
    private InterviewAnalysisRepository interviewAnalysisRepository;

    public Interview startInterview(Long userId, String targetRole) {
        Interview interview = new Interview(userId, targetRole);
        Interview saved = interviewRepository.save(interview);

        List<InterviewQuestion> questions = new ArrayList<>();
        if (targetRole.toLowerCase().contains("data") || targetRole.toLowerCase().contains("analytics")) {
            questions.add(new InterviewQuestion(saved.getId(), "Explain the difference between INNER JOIN, LEFT JOIN, and RIGHT JOIN in SQL with a practical example.", "Technical"));
            questions.add(new InterviewQuestion(saved.getId(), "How do you handle missing values or outliers in a large dataset before analysis?", "Situational"));
            questions.add(new InterviewQuestion(saved.getId(), "Describe a time when your analytical insights directly influenced a project or decision.", "Behavioral"));
            questions.add(new InterviewQuestion(saved.getId(), "Why are you interested in pursuing a career in Data Analytics with our organization?", "HR"));
        } else {
            questions.add(new InterviewQuestion(saved.getId(), "Explain object-oriented programming principles (Encapsulation, Inheritance, Polymorphism, Abstraction) in Java.", "Technical"));
            questions.add(new InterviewQuestion(saved.getId(), "How would you design a RESTful API endpoint that handles high traffic spikes gracefully?", "Situational"));
            questions.add(new InterviewQuestion(saved.getId(), "Describe a complex bug you encountered in a project and how you systematically debugged it.", "Behavioral"));
            questions.add(new InterviewQuestion(saved.getId(), "What are your core strengths and how do you align them with your career goal as a " + targetRole + "?", "HR"));
        }

        for (InterviewQuestion q : questions) {
            interviewQuestionRepository.save(q);
        }

        return saved;
    }

    public List<InterviewQuestion> getInterviewQuestions(Long interviewId) {
        return interviewQuestionRepository.findByInterviewId(interviewId);
    }

    public InterviewAnalysis submitAnswersAndAnalyze(Long interviewId, Map<Long, String> questionAnswerMap) {
        Interview interview = interviewRepository.findById(interviewId).orElseThrow();

        int totalQuestions = questionAnswerMap.size();
        int totalScore = 0;

        for (Map.Entry<Long, String> entry : questionAnswerMap.entrySet()) {
            Long qId = entry.getKey();
            String ans = entry.getValue();

            int score = (ans != null && ans.trim().length() > 30) ? 85 : (ans != null && !ans.trim().isEmpty() ? 65 : 40);
            totalScore += score;

            String feedback = score >= 80 ? "Excellent structured response with key technical terminology." : "Good effort. Consider adding specific examples and STAR method structure.";

            InterviewAnswer answer = new InterviewAnswer(interviewId, qId, ans, score, feedback);
            interviewAnswerRepository.save(answer);
        }

        int overallScore = totalQuestions > 0 ? totalScore / totalQuestions : 75;
        interview.setOverallScore(overallScore);
        interviewRepository.save(interview);

        String missingSkills = "System Design Depth, STAR Behavioral Framing, Advanced Framework Concepts";
        
        String improvementPlan = "### 🎯 Custom Interview Improvement Plan for " + interview.getTargetRole() + "\n\n" +
                "1. **Technical Depth**: Review core concurrency, data structures, and system design patterns.\n" +
                "2. **STAR Response Structure**: Practice articulating Situation, Task, Action, and Result in under 2 minutes.\n" +
                "3. **Mock Voice Practice**: Rehearse responses out loud to improve fluency, confidence, and pacing.\n" +
                "4. **Targeted Skill Refresh**: Complete QUEST Skill courses in " + interview.getTargetRole() + " priority areas.";

        InterviewAnalysis analysis = new InterviewAnalysis(
            interviewId, interview.getUserId(), overallScore, overallScore + 5, overallScore - 2, missingSkills, improvementPlan
        );

        return interviewAnalysisRepository.save(analysis);
    }

    public Optional<InterviewAnalysis> getAnalysisForInterview(Long interviewId) {
        return interviewAnalysisRepository.findByInterviewId(interviewId);
    }
}
