package com.godstime.dlcfLagos.web_app.services;

import com.godstime.dlcfLagos.web_app.dto.FellowshipCenterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FellowshipCenterService {
    FellowshipCenterDTO createFellowshipCenter(FellowshipCenterDTO centerDTO, Long userId);
    
    FellowshipCenterDTO updateFellowshipCenter(Long id, FellowshipCenterDTO centerDTO, Long userId);
    
    void deleteFellowshipCenter(Long id, Long userId);
    
    FellowshipCenterDTO getFellowshipCenterById(Long id);
    
    Page<FellowshipCenterDTO> getAllFellowshipCenters(Pageable pageable);
    
    Page<FellowshipCenterDTO> getActiveFellowshipCenters(Pageable pageable);
    
    Page<FellowshipCenterDTO> getFellowshipCentersByCity(String city, Pageable pageable);
    
    Page<FellowshipCenterDTO> getFellowshipCentersByState(String state, Pageable pageable);
    
    Page<FellowshipCenterDTO> getFellowshipCentersByUser(Long userId, Pageable pageable);
    
    long countActiveFellowshipCenters();
    
    long countFellowshipCentersByCity(String city);
    
    long countFellowshipCentersByState(String state);
    
    boolean existsById(Long id);
    
    FellowshipCenterDTO toggleFellowshipCenterStatus(Long id, Long userId);
} 