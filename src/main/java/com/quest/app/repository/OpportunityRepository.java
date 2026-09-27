package com.quest.app.repository;

import com.quest.app.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findByCategory(String category);
    List<Opportunity> findByMode(String mode);
}
