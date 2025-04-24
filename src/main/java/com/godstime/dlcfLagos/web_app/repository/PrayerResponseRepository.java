package com.godstime.dlcfLagos.web_app.repository;

import com.godstime.dlcfLagos.web_app.model.PrayerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PrayerResponseRepository extends JpaRepository<PrayerResponse, Long>, JpaSpecificationExecutor<PrayerResponse> {
    
    Page<PrayerResponse> findByPrayerRequestId(Long prayerRequestId, Pageable pageable);
    
    Page<PrayerResponse> findByRespondedById(Long userId, Pageable pageable);
    
    Page<PrayerResponse> findByPrayerRequestIdAndIsAnonymousFalse(Long prayerRequestId, Pageable pageable);
    
    long countByPrayerRequestId(Long prayerRequestId);
} 