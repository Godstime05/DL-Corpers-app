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
    
    List<Announcement> findByActiveTrueOrderByCreatedAtDesc();
    
    Page<Announcement> findByActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    
    List<Announcement> findByCreatedByIdAndActiveTrueOrderByCreatedAtDesc(Long userId);
    
    List<Announcement> findByActiveTrue();
    
    List<Announcement> findByPriority(String priority);
    
    List<Announcement> findByTargetAudience(String targetAudience);
    
    List<Announcement> findByTargetAudienceAndSpecificTarget(String targetAudience, String specificTarget);
    
    List<Announcement> findByCreatedById(Long userId);
} 