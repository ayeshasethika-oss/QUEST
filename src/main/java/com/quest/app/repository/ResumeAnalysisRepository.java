package com.quest.app.repository;

import com.quest.app.model.ResumeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis, Long> {
    Optional<ResumeAnalysis> findFirstByUserIdOrderByAnalyzedAtDesc(Long userId);
    Optional<ResumeAnalysis> findByResumeId(Long resumeId);
}
