package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.Feedback;
import com.godstime.dlcfLagos.web_app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    List<Feedback> findBySubmittedBy(User submittedBy);
    
    List<Feedback> findByRespondedBy(User respondedBy);
    
    List<Feedback> findByType(Feedback.FeedbackType type);
    
    List<Feedback> findByStatus(Feedback.FeedbackStatus status);
    
    List<Feedback> findByPriority(Feedback.FeedbackPriority priority);
    
    List<Feedback> findByIsAnonymous(boolean isAnonymous);
    
    List<Feedback> findBySubmittedByAndIsAnonymousFalse(User submittedBy);
    
    List<Feedback> findBySubmittedByAndStatus(User submittedBy, Feedback.FeedbackStatus status);
    
    List<Feedback> findByRespondedByAndStatus(User respondedBy, Feedback.FeedbackStatus status);
    
    List<Feedback> findByTypeAndStatus(Feedback.FeedbackType type, Feedback.FeedbackStatus status);
    
    List<Feedback> findByPriorityAndStatus(Feedback.FeedbackPriority priority, Feedback.FeedbackStatus status);
    
    List<Feedback> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Feedback> findByRespondedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    long countByStatus(Feedback.FeedbackStatus status);
    
    long countByType(Feedback.FeedbackType type);
    
    long countByPriority(Feedback.FeedbackPriority priority);
    
    long countBySubmittedBy(User submittedBy);
    
    long countByRespondedBy(User respondedBy);
    
    List<Feedback> findBySubmittedByIdOrderBySubmittedAtDesc(Long userId);
    
    Page<Feedback> findBySubmittedByIdOrderBySubmittedAtDesc(Long userId, Pageable pageable);
    
    List<Feedback> findByRespondedByIdOrderByRespondedAtDesc(Long userId);
    
    List<Feedback> findByRespondedAtIsNullOrderBySubmittedAtDesc();
    
    List<Feedback> findByIsAnonymousFalseOrderBySubmittedAtDesc();
} 