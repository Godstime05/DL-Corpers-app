package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.EvangelismRecord;
import com.godstime.dlcfLagos.web_app.models.FellowshipCenter;
import com.godstime.dlcfLagos.web_app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EvangelismRecordRepository extends JpaRepository<EvangelismRecord, Long> {
    List<EvangelismRecord> findByFellowshipCenter(FellowshipCenter center);
    
    List<EvangelismRecord> findByCreatedBy(User user);
    
    List<EvangelismRecord> findByEvangelismDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<EvangelismRecord> findByLocationContainingIgnoreCase(String location);
    
    Page<EvangelismRecord> findAll(Pageable pageable);
    
    Page<EvangelismRecord> findByFellowshipCenter(FellowshipCenter center, Pageable pageable);
    
    Page<EvangelismRecord> findByCreatedBy(User user, Pageable pageable);
    
    Page<EvangelismRecord> findByEvangelismDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    Page<EvangelismRecord> findByLocationContainingIgnoreCase(String location, Pageable pageable);
    
    long countByFellowshipCenter(FellowshipCenter center);
    
    long countByCreatedBy(User user);
    
    long countByEvangelismDateBetween(LocalDateTime startDate, LocalDateTime endDate);
} 