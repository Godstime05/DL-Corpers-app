package com.godstime.dlcfLagos.web_app.services;

import com.godstime.dlcfLagos.web_app.dto.AnnouncementDTO;

import java.util.List;

public interface AnnouncementService {
    
    AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO);
    
    AnnouncementDTO updateAnnouncement(Long id, AnnouncementDTO announcementDTO);
    
    void deleteAnnouncement(Long id);
    
    AnnouncementDTO getAnnouncementById(Long id);
    
    List<AnnouncementDTO> getAllAnnouncements();
    
    List<AnnouncementDTO> getActiveAnnouncements();
    
    List<AnnouncementDTO> getAnnouncementsByPriority(String priority);
    
    List<AnnouncementDTO> getAnnouncementsByTargetAudience(String targetAudience, String specificTarget);
    
    List<AnnouncementDTO> getAnnouncementsByCreator(Long userId);
    
    void toggleAnnouncementStatus(Long id);
} 