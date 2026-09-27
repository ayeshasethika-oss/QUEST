package com.quest.app.repository;

import com.quest.app.model.SavedOpportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedOpportunityRepository extends JpaRepository<SavedOpportunity, Long> {
    List<SavedOpportunity> findByUserId(Long userId);
    Optional<SavedOpportunity> findByUserIdAndOpportunityId(Long userId, Long opportunityId);
    void deleteByUserIdAndOpportunityId(Long userId, Long opportunityId);
}
