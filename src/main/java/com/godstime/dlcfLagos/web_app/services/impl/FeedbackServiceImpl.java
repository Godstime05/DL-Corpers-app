package com.godstime.dlcfLagos.web_app.services.impl;

import com.godstime.dlcfLagos.web_app.dto.FeedbackDTO;
import com.godstime.dlcfLagos.web_app.models.Feedback;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.repositories.FeedbackRepository;
import com.godstime.dlcfLagos.web_app.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public FeedbackDTO createFeedback(FeedbackDTO feedbackDTO, User submittedBy) {
        Feedback feedback = new Feedback();
        mapDTOToEntity(feedbackDTO, feedback);
        feedback.setSubmittedBy(submittedBy);
        feedback.setSubmittedAt(LocalDateTime.now());
        feedback.setStatus(Feedback.FeedbackStatus.PENDING);
        return mapEntityToDTO(feedbackRepository.save(feedback));
    }

    @Override
    public FeedbackDTO updateFeedback(Long id, FeedbackDTO feedbackDTO, User updatedBy) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        
        if (!feedback.getSubmittedBy().equals(updatedBy)) {
            throw new RuntimeException("Unauthorized to update this feedback");
        }
        
        mapDTOToEntity(feedbackDTO, feedback);
        feedback.setUpdatedAt(LocalDateTime.now());
        return mapEntityToDTO(feedbackRepository.save(feedback));
    }

    @Override
    public FeedbackDTO respondToFeedback(Long id, String response, User respondedBy) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        
        feedback.setResponse(response);
        feedback.setRespondedBy(respondedBy);
        feedback.setRespondedAt(LocalDateTime.now());
        feedback.setStatus(Feedback.FeedbackStatus.RESOLVED);
        
        return mapEntityToDTO(feedbackRepository.save(feedback));
    }

    @Override
    public FeedbackDTO updateFeedbackStatus(Long id, Feedback.FeedbackStatus status, User updatedBy) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        
        feedback.setStatus(status);
        feedback.setUpdatedAt(LocalDateTime.now());
        return mapEntityToDTO(feedbackRepository.save(feedback));
    }

    @Override
    public FeedbackDTO updateFeedbackPriority(Long id, Feedback.FeedbackPriority priority, User updatedBy) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        
        feedback.setPriority(priority);
        feedback.setUpdatedAt(LocalDateTime.now());
        return mapEntityToDTO(feedbackRepository.save(feedback));
    }

    @Override
    public void deleteFeedback(Long id, User deletedBy) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        
        if (!feedback.getSubmittedBy().equals(deletedBy)) {
            throw new RuntimeException("Unauthorized to delete this feedback");
        }
        
        feedbackRepository.delete(feedback);
    }

    @Override
    public FeedbackDTO getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .map(this::mapEntityToDTO)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    @Override
    public List<FeedbackDTO> getAllFeedback() {
        return feedbackRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> getFeedbackByUser(User user) {
        return feedbackRepository.findBySubmittedBy(user).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> getFeedbackByType(Feedback.FeedbackType type) {
        return feedbackRepository.findByType(type).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> getFeedbackByStatus(Feedback.FeedbackStatus status) {
        return feedbackRepository.findByStatus(status).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> getFeedbackByPriority(Feedback.FeedbackPriority priority) {
        return feedbackRepository.findByPriority(priority).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> getAnonymousFeedback() {
        return feedbackRepository.findByIsAnonymous(true).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> getFeedbackByDateRange(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        
        return feedbackRepository.findBySubmittedAtBetween(start, end).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countFeedbackByStatus(Feedback.FeedbackStatus status) {
        return feedbackRepository.countByStatus(status);
    }

    @Override
    public long countFeedbackByType(Feedback.FeedbackType type) {
        return feedbackRepository.countByType(type);
    }

    @Override
    public long countFeedbackByPriority(Feedback.FeedbackPriority priority) {
        return feedbackRepository.countByPriority(priority);
    }

    @Override
    public boolean existsById(Long id) {
        return feedbackRepository.existsById(id);
    }

    private FeedbackDTO mapEntityToDTO(Feedback feedback) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(feedback.getId());
        dto.setTitle(feedback.getTitle());
        dto.setContent(feedback.getContent());
        dto.setType(feedback.getType());
        dto.setStatus(feedback.getStatus());
        dto.setPriority(feedback.getPriority());
        dto.setAnonymous(feedback.isAnonymous());
        dto.setResponse(feedback.getResponse());
        dto.setSubmittedById(feedback.getSubmittedBy().getId());
        dto.setRespondedById(feedback.getRespondedBy() != null ? feedback.getRespondedBy().getId() : null);
        dto.setSubmittedAt(feedback.getSubmittedAt());
        dto.setRespondedAt(feedback.getRespondedAt());
        return dto;
    }

    private void mapDTOToEntity(FeedbackDTO dto, Feedback feedback) {
        feedback.setTitle(dto.getTitle());
        feedback.setContent(dto.getContent());
        feedback.setType(dto.getType());
        feedback.setPriority(dto.getPriority());
        feedback.setAnonymous(dto.isAnonymous());
    }
} 