package com.godstime.dlcfLagos.web_app.repository;

import com.godstime.dlcfLagos.web_app.model.FellowshipCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FellowshipCenterRepository extends JpaRepository<FellowshipCenter, Long> {
    
    List<FellowshipCenter> findByIsActiveTrueOrderByNameAsc();
    
    List<FellowshipCenter> findByLgaAndIsActiveTrueOrderByNameAsc(String lga);
    
    List<FellowshipCenter> findByZoneAndIsActiveTrueOrderByNameAsc(String zone);
    
    List<FellowshipCenter> findByCoordinatorIdAndIsActiveTrueOrderByNameAsc(Long userId);
    
    List<FellowshipCenter> findByNameContainingIgnoreCaseAndIsActiveTrueOrderByNameAsc(String name);
    
    List<FellowshipCenter> findByAddressContainingIgnoreCaseAndIsActiveTrueOrderByNameAsc(String address);
} 