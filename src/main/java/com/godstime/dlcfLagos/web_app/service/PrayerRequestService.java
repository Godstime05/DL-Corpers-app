package com.godstime.dlcfLagos.web_app.service;

import com.godstime.dlcfLagos.web_app.dto.PrayerRequestDTO;
import com.godstime.dlcfLagos.web_app.model.PrayerRequest;
import com.godstime.dlcfLagos.web_app.model.User;

import java.util.List;

public interface PrayerRequestService {
    
    PrayerRequestDTO createPrayerRequest(PrayerRequestDTO prayerRequestDTO, User submittedBy);
    
    PrayerRequestDTO updatePrayerRequest(Long id, PrayerRequestDTO prayerRequestDTO, User updatedBy);
    
    PrayerRequestDTO respondToPrayerRequest(Long id, String response, User respondedBy);
    
    PrayerRequestDTO updatePrayerRequestStatus(Long id, PrayerRequest.PrayerStatus status, User updatedBy);
    
    void deletePrayerRequest(Long id, User deletedBy);
    
    PrayerRequestDTO getPrayerRequestById(Long id);
    
    List<PrayerRequestDTO> getAllPrayerRequests();
    
    List<PrayerRequestDTO> getPrayerRequestsByUser(User user);
    
    List<PrayerRequestDTO> getPrayerRequestsByCategory(PrayerRequest.PrayerCategory category);
    
    List<PrayerRequestDTO> getPrayerRequestsByStatus(PrayerRequest.PrayerStatus status);
    
    List<PrayerRequestDTO> getAnonymousPrayerRequests();
    
    List<PrayerRequestDTO> getAnsweredPrayerRequests();
    
    List<PrayerRequestDTO> getUnansweredPrayerRequests();
    
    List<PrayerRequestDTO> getMostPrayedForRequests(int limit);
    
    PrayerRequestDTO incrementPrayerCount(Long id);
    
    long countPrayerRequestsByStatus(PrayerRequest.PrayerStatus status);
    
    long countPrayerRequestsByCategory(PrayerRequest.PrayerCategory category);
    
    boolean existsById(Long id);
} 