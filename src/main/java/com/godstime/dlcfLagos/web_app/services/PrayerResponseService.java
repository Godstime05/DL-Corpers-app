package com.godstime.dlcfLagos.web_app.services;

import com.godstime.dlcfLagos.web_app.dto.PrayerResponseDTO;
import com.godstime.dlcfLagos.web_app.models.PrayerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PrayerResponseService {
    
    PrayerResponseDTO createResponse(PrayerResponseDTO responseDTO);
    
    PrayerResponseDTO updateResponse(Long id, PrayerResponseDTO responseDTO);
    
    void deleteResponse(Long id);
    
    PrayerResponseDTO getResponseById(Long id);
    
    Page<PrayerResponseDTO> getResponsesByPrayerRequest(
            Long prayerRequestId,
            Pageable pageable,
            PrayerResponse.ResponseType type,
            Boolean isAnonymous,
            LocalDateTime startDate,
            LocalDateTime endDate);
    
    Page<PrayerResponseDTO> getResponsesByUser(Long userId, Pageable pageable);
    
    Page<PrayerResponseDTO> getNonAnonymousResponses(Long prayerRequestId, Pageable pageable);
    
    long countResponses(Long prayerRequestId);
    
    Page<PrayerResponseDTO> getRecentResponses(Pageable pageable, PrayerResponse.ResponseType type);
} 