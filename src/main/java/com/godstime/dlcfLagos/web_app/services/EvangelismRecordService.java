package com.godstime.dlcfLagos.web_app.services;

import com.godstime.dlcfLagos.web_app.dto.EvangelismRecordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface EvangelismRecordService {
    EvangelismRecordDTO createEvangelismRecord(EvangelismRecordDTO recordDTO, Long userId);
    
    EvangelismRecordDTO updateEvangelismRecord(Long id, EvangelismRecordDTO recordDTO, Long userId);
    
    void deleteEvangelismRecord(Long id, Long userId);
    
    EvangelismRecordDTO getEvangelismRecordById(Long id);
    
    Page<EvangelismRecordDTO> getAllEvangelismRecords(Pageable pageable);
    
    Page<EvangelismRecordDTO> getEvangelismRecordsByFellowshipCenter(Long centerId, Pageable pageable);
    
    Page<EvangelismRecordDTO> getEvangelismRecordsByUser(Long userId, Pageable pageable);
    
    Page<EvangelismRecordDTO> getEvangelismRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    Page<EvangelismRecordDTO> getEvangelismRecordsByLocation(String location, Pageable pageable);
    
    long countEvangelismRecordsByFellowshipCenter(Long centerId);
    
    long countEvangelismRecordsByUser(Long userId);
    
    long countEvangelismRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    boolean existsById(Long id);
    
    EvangelismRecordDTO addTeamMember(Long recordId, Long userId, Long currentUserId);
    
    EvangelismRecordDTO removeTeamMember(Long recordId, Long userId, Long currentUserId);
} 