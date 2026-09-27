package com.quest.app.repository;

import com.quest.app.model.InterviewAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewAnalysisRepository extends JpaRepository<InterviewAnalysis, Long> {
    Optional<InterviewAnalysis> findByInterviewId(Long interviewId);
}
