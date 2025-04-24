package com.godstime.dlcfLagos.web_app.repository;

import com.godstime.dlcfLagos.web_app.model.EvangelismRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EvangelismRecordRepository extends JpaRepository<EvangelismRecord, Long> {
    
    List<EvangelismRecord> findByCreatedByIdOrderByEventDateDesc(Long userId);
    
    Page<EvangelismRecord> findByCreatedByIdOrderByEventDateDesc(Long userId, Pageable pageable);
    
    List<EvangelismRecord> findByEventDateBetweenOrderByEventDateDesc(LocalDateTime startDate, LocalDateTime endDate);
    
    List<EvangelismRecord> findByParticipantsIdOrderByEventDateDesc(Long userId);
    
    List<EvangelismRecord> findByLocationContainingIgnoreCaseOrderByEventDateDesc(String location);
    
    List<EvangelismRecord> findBySoulsWonGreaterThanOrderByEventDateDesc(int soulsWon);
} 