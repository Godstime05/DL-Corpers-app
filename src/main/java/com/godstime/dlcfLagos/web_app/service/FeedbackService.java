package com.godstime.dlcfLagos.web_app.service;

import com.godstime.dlcfLagos.web_app.dto.FeedbackDTO;
import com.godstime.dlcfLagos.web_app.model.Feedback;
import com.godstime.dlcfLagos.web_app.model.User;

import java.util.List;

public interface FeedbackService {
    
    FeedbackDTO createFeedback(FeedbackDTO feedbackDTO, User submittedBy);
    
    FeedbackDTO updateFeedback(Long id, FeedbackDTO feedbackDTO, User updatedBy);
    
    FeedbackDTO respondToFeedback(Long id, String response, User respondedBy);
    
    FeedbackDTO updateFeedbackStatus(Long id, Feedback.FeedbackStatus status, User updatedBy);
    
    FeedbackDTO updateFeedbackPriority(Long id, Feedback.FeedbackPriority priority, User updatedBy);
    
    void deleteFeedback(Long id, User deletedBy);
    
    FeedbackDTO getFeedbackById(Long id);
    
    List<FeedbackDTO> getAllFeedback();
    
    List<FeedbackDTO> getFeedbackByUser(User user);
    
    List<FeedbackDTO> getFeedbackByType(Feedback.FeedbackType type);
    
    List<FeedbackDTO> getFeedbackByStatus(Feedback.FeedbackStatus status);
    
    List<FeedbackDTO> getFeedbackByPriority(Feedback.FeedbackPriority priority);
    
    List<FeedbackDTO> getAnonymousFeedback();
    
    List<FeedbackDTO> getFeedbackByDateRange(String startDate, String endDate);
    
    long countFeedbackByStatus(Feedback.FeedbackStatus status);
    
    long countFeedbackByType(Feedback.FeedbackType type);
    
    long countFeedbackByPriority(Feedback.FeedbackPriority priority);
    
    boolean existsById(Long id);
} 