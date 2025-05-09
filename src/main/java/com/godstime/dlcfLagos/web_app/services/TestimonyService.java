package com.godstime.dlcfLagos.web_app.services;

import com.godstime.dlcfLagos.web_app.dto.TestimonyDTO;
import com.godstime.dlcfLagos.web_app.models.Testimony;
import com.godstime.dlcfLagos.web_app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TestimonyService {
    
    TestimonyDTO createTestimony(TestimonyDTO testimonyDTO, User submittedBy);
    
    TestimonyDTO updateTestimony(Long id, TestimonyDTO testimonyDTO, User user);
    
    void deleteTestimony(Long id, User user);
    
    TestimonyDTO getTestimonyById(Long id);
    
    Page<TestimonyDTO> getAllTestimonies(Pageable pageable);
    List<TestimonyDTO> getAllTheTestimonies();
    
    Page<TestimonyDTO> getTestimoniesByStatus(Testimony.TestimonyStatus status, Pageable pageable);
    
    Page<TestimonyDTO> getTestimoniesByCategory(Testimony.TestimonyCategory category, Pageable pageable);
    
    Page<TestimonyDTO> getTestimoniesByUser(User user, Pageable pageable);
    
    Page<TestimonyDTO> getTestimoniesByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    TestimonyDTO approveTestimony(Long id, User approver);
    
    TestimonyDTO rejectTestimony(Long id, User rejector, String rejectionReason);
    
    TestimonyDTO likeTestimony(Long id, User user);
    
    TestimonyDTO unlikeTestimony(Long id, User user);
    
    long countTestimoniesByStatus(Testimony.TestimonyStatus status);
    
    long countTestimoniesByCategory(Testimony.TestimonyCategory category);

    TestimonyDTO shareTestimony(Long id);
    
    boolean existsById(Long id);

    List<TestimonyDTO> getMostSharedTestimonies(int limit);

    List<TestimonyDTO> getMostLikedTestimonies(int limit);
} 