package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.PrayerRequest;
import com.godstime.dlcfLagos.web_app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrayerRequestRepository extends JpaRepository<PrayerRequest, Long> {
    
    List<PrayerRequest> findBySubmittedBy(User submittedBy);
    
    List<PrayerRequest> findByRespondedBy(User respondedBy);
    
    List<PrayerRequest> findByCategory(PrayerRequest.PrayerCategory category);
    
    List<PrayerRequest> findByStatus(PrayerRequest.PrayerStatus status);
    
    List<PrayerRequest> findByStatusNot(PrayerRequest.PrayerStatus status);
    
    List<PrayerRequest> findByIsAnonymous(boolean isAnonymous);
    
    List<PrayerRequest> findBySubmittedByAndIsAnonymousFalse(User submittedBy);
    
    List<PrayerRequest> findBySubmittedByAndStatus(User submittedBy, PrayerRequest.PrayerStatus status);
    
    List<PrayerRequest> findByRespondedByAndStatus(User respondedBy, PrayerRequest.PrayerStatus status);
    
    List<PrayerRequest> findByCategoryAndStatus(PrayerRequest.PrayerCategory category, PrayerRequest.PrayerStatus status);
    
    List<PrayerRequest> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<PrayerRequest> findByRespondedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    long countByStatus(PrayerRequest.PrayerStatus status);
    
    long countByCategory(PrayerRequest.PrayerCategory category);
    
    long countBySubmittedBy(User submittedBy);
    
    long countByRespondedBy(User respondedBy);
    
    Page<PrayerRequest> findAll(Pageable pageable);
} 