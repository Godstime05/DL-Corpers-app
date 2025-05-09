package com.godstime.dlcfLagos.web_app.services.impl;

import com.godstime.dlcfLagos.web_app.dto.AnnouncementDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.models.Announcement;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.repositories.AnnouncementRepository;
import com.godstime.dlcfLagos.web_app.services.AnnouncementService;
import com.godstime.dlcfLagos.web_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {
    
    @Autowired
    private AnnouncementRepository announcementRepository;
    
    @Autowired
    private UserService userService;
    
    @Override
    public AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found");
        }
        
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setCreatedBy(currentUser);
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setUpdatedAt(LocalDateTime.now());
        announcement.setActive(true);
        announcement.setPriority(announcementDTO.getPriority());
        announcement.setTargetAudience(announcementDTO.getTargetAudience());
        announcement.setSpecificTarget(announcementDTO.getSpecificTarget());
        
        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return convertToDTO(savedAnnouncement);
    }
    
    @Override
    public AnnouncementDTO updateAnnouncement(Long id, AnnouncementDTO announcementDTO) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + id));
        
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setUpdatedAt(LocalDateTime.now());
        announcement.setPriority(announcementDTO.getPriority());
        announcement.setTargetAudience(announcementDTO.getTargetAudience());
        announcement.setSpecificTarget(announcementDTO.getSpecificTarget());
        
        Announcement updatedAnnouncement = announcementRepository.save(announcement);
        return convertToDTO(updatedAnnouncement);
    }
    
    @Override
    public void deleteAnnouncement(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Announcement not found with id: " + id);
        }
        announcementRepository.deleteById(id);
    }
    
    @Override
    public AnnouncementDTO getAnnouncementById(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + id));
        return convertToDTO(announcement);
    }
    
    @Override
    public List<AnnouncementDTO> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AnnouncementDTO> getActiveAnnouncements() {
        return announcementRepository.findByActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AnnouncementDTO> getAnnouncementsByPriority(String priority) {
        if (priority == null || priority.isEmpty()) {
            throw new IllegalArgumentException("Priority cannot be null or empty");
        }
        return announcementRepository.findByPriority(priority).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AnnouncementDTO> getAnnouncementsByTargetAudience(String targetAudience, String specificTarget) {
        if (targetAudience == null || targetAudience.isEmpty()) {
            throw new IllegalArgumentException("Target audience cannot be null or empty");
        }
        
        if (specificTarget != null && !specificTarget.isEmpty()) {
            return announcementRepository.findByTargetAudienceAndSpecificTarget(targetAudience, specificTarget).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return announcementRepository.findByTargetAudience(targetAudience).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AnnouncementDTO> getAnnouncementsByCreator(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return announcementRepository.findByCreatedById(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void toggleAnnouncementStatus(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + id));
        announcement.setActive(!announcement.isActive());
        announcementRepository.save(announcement);
    }
    
    private AnnouncementDTO convertToDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setCreatedBy(announcement.getCreatedBy().getId());
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setUpdatedAt(announcement.getUpdatedAt());
        dto.setActive(announcement.isActive());
        dto.setPriority(announcement.getPriority());
        dto.setTargetAudience(announcement.getTargetAudience());
        dto.setSpecificTarget(announcement.getSpecificTarget());
        return dto;
    }
} 