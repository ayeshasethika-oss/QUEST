package com.quest.app.repository;

import com.quest.app.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByUserId(Long userId);
    Optional<Interview> findFirstByUserIdOrderByCompletedAtDesc(Long userId);
}
