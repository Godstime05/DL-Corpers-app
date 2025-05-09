package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    
    List<Announcement> findByIsActiveTrueOrderByCreatedAtDesc();
    
    Page<Announcement> findByIsActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    
    List<Announcement> findByCategoryAndIsActiveTrueOrderByCreatedAtDesc(String category);
    
    List<Announcement> findByEventDateBetweenAndIsActiveTrueOrderByEventDateAsc(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Announcement> findByCreatedByIdAndIsActiveTrueOrderByCreatedAtDesc(Long userId);
    
    List<Announcement> findByActiveTrue();
    
    List<Announcement> findByPriority(String priority);
    
    List<Announcement> findByTargetAudience(String targetAudience);
    
    List<Announcement> findByTargetAudienceAndSpecificTarget(String targetAudience, String specificTarget);
    
    List<Announcement> findByCreatedById(Long userId);
    
    List<Announcement> findAll();
} 