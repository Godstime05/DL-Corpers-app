package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    
    List<Resource> findByIsActiveTrueOrderByCreatedAtDesc();
    
    Page<Resource> findByIsActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    
    List<Resource> findByCategoryAndIsActiveTrueOrderByCreatedAtDesc(String category);
    
    List<Resource> findByTypeAndIsActiveTrueOrderByCreatedAtDesc(String type);
    
    List<Resource> findByCreatedByIdAndIsActiveTrueOrderByCreatedAtDesc(Long userId);
    
    List<Resource> findByCategoryAndTypeAndIsActiveTrueOrderByCreatedAtDesc(String category, String type);
} 