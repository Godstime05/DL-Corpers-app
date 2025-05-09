package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.FellowshipCenter;
import com.godstime.dlcfLagos.web_app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FellowshipCenterRepository extends JpaRepository<FellowshipCenter, Long> {
    
    List<FellowshipCenter> findByIsActiveTrue();
    
    List<FellowshipCenter> findByCity(String city);
    
    List<FellowshipCenter> findByState(String state);
    
    List<FellowshipCenter> findByCreatedBy(User user);
    
    Page<FellowshipCenter> findAll(Pageable pageable);
    
    Page<FellowshipCenter> findByIsActiveTrue(Pageable pageable);
    
    Page<FellowshipCenter> findByCity(String city, Pageable pageable);
    
    Page<FellowshipCenter> findByState(String state, Pageable pageable);
    
    Page<FellowshipCenter> findByCreatedBy(User user, Pageable pageable);
    
    long countByIsActiveTrue();
    
    long countByCity(String city);
    
    long countByState(String state);
} 