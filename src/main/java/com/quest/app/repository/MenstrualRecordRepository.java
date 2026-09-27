package com.quest.app.repository;

import com.quest.app.model.MenstrualRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenstrualRecordRepository extends JpaRepository<MenstrualRecord, Long> {
    List<MenstrualRecord> findByUserIdOrderByStartDateDesc(Long userId);
    Optional<MenstrualRecord> findFirstByUserIdOrderByStartDateDesc(Long userId);
}
