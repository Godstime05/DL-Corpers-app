package com.godstime.dlcfLagos.web_app.repository;

import com.godstime.dlcfLagos.web_app.model.Testimony;
import com.godstime.dlcfLagos.web_app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TestimonyRepository extends JpaRepository<Testimony, Long> {
    
    List<Testimony> findBySubmittedBy(User submittedBy);
    
    List<Testimony> findByApprovedBy(User approvedBy);
    
    List<Testimony> findByCategory(Testimony.TestimonyCategory category);
    
    List<Testimony> findByStatus(Testimony.TestimonyStatus status);
    
    List<Testimony> findByIsAnonymous(boolean isAnonymous);

    List<Testimony> findByLikesCountGreaterThanEqual(int minLikes);
    
    List<Testimony> findBySharesCountGreaterThanEqual(int minShares);
    
    long countByStatus(Testimony.TestimonyStatus status);
    
    long countByCategory(Testimony.TestimonyCategory category);

    Page<Testimony> findAll(Pageable pageable);

    Page<Testimony> findByStatus(Testimony.TestimonyStatus status, Pageable pageable);

    Page<Testimony> findByCategory(Testimony.TestimonyCategory category, Pageable pageable);


    // Additional methods to match service implementation
    Page<Testimony> findBySubmittedBy(User user, Pageable pageable);
    
    Page<Testimony> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    Page<Testimony> findByStatusAndCategory(Testimony.TestimonyStatus status, Testimony.TestimonyCategory category, Pageable pageable);

    Page<Testimony> findByApprovedBy(User approver, Pageable pageable);

    //    // Added methods for sorting by popularity
    //    List<Testimony> findAllByOrderByLikesCountDesc();
    //    List<Testimony> findAllByOrderBySharesCountDesc();
    //
    //    // Added methods for finding by status and sorting
    //    List<Testimony> findByStatusOrderBySubmittedAtDesc(Testimony.TestimonyStatus status);
    //    List<Testimony> findByStatusOrderByLikesCountDesc(Testimony.TestimonyStatus status);
    //
    //    // Added methods for finding recent testimonies
    //    List<Testimony> findAllByOrderBySubmittedAtDesc();
    //    List<Testimony> findByStatusAndSubmittedAtAfter(Testimony.TestimonyStatus status, LocalDateTime date);
    //
    //    // Added method for finding testimonies by multiple statuses
    //    List<Testimony> findByStatusIn(List<Testimony.TestimonyStatus> statuses);
} 