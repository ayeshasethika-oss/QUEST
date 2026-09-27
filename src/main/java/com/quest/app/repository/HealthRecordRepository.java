package com.quest.app.repository;

import com.quest.app.model.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    List<HealthRecord> findByUserIdOrderByRecordDateDesc(Long userId);
    Optional<HealthRecord> findFirstByUserIdOrderByRecordDateDesc(Long userId);
}
