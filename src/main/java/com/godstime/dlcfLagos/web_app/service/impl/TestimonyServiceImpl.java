package com.godstime.dlcfLagos.web_app.service.impl;

import com.godstime.dlcfLagos.web_app.dto.TestimonyDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.exception.UnauthorizedException;
import com.godstime.dlcfLagos.web_app.model.Testimony;
import com.godstime.dlcfLagos.web_app.model.User;
import com.godstime.dlcfLagos.web_app.repository.TestimonyRepository;
import com.godstime.dlcfLagos.web_app.repository.UserRepository;
import com.godstime.dlcfLagos.web_app.service.TestimonyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TestimonyServiceImpl implements TestimonyService {

    private final TestimonyRepository testimonyRepository;
    private final UserRepository userRepository;

    @Override
    public TestimonyDTO createTestimony(TestimonyDTO testimonyDTO, User submittedBy) {
        User user = userRepository.findById(submittedBy.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ submittedBy));

        Testimony testimony = new Testimony();
        testimony.setTitle(testimonyDTO.getTitle());
        testimony.setContent(testimonyDTO.getContent());
        testimony.setCategory(testimonyDTO.getCategory());
        testimony.setCategory(testimonyDTO.getCategory());
        testimony.setStatus(Testimony.TestimonyStatus.PENDING);
        testimony.setSubmittedBy(user);
        testimony.setSubmittedAt(LocalDateTime.now());
        testimony.setLikesCount(0);
        testimony.setSharesCount(0);
        testimony.setLikedBy(new HashSet<>());

        return TestimonyDTO.fromEntity(testimonyRepository.save(testimony));
    }

    @Override
    public TestimonyDTO updateTestimony(Long id, TestimonyDTO testimonyDTO, User user) {
        Testimony testimony = testimonyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: " + id));

        if (!testimony.getSubmittedBy().getId().equals(user)){
            throw new UnauthorizedException("You are not authorized to update this testimony");
        }
        testimony.setTitle(testimonyDTO.getTitle());
        testimony.setContent(testimonyDTO.getContent());
        testimony.setCategory(testimonyDTO.getCategory());
        testimony.setUpdatedAt(LocalDateTime.now());

        return TestimonyDTO.fromEntity(testimonyRepository.save(testimony));
    }

    @Override
    public void deleteTestimony(Long id, User user) {
        Testimony testimony = testimonyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: " + id));
        if (!testimony.getSubmittedBy().getId().equals(user)){
            throw new UnauthorizedException("You are not authorized to delete this testimony");
        }
        testimonyRepository.delete(testimony);

    }

    @Override
    public TestimonyDTO getTestimonyById(Long id) {
        Testimony testimony = testimonyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: " +id));

        return TestimonyDTO.fromEntity(testimony);
    }

    @Override
    public Page<TestimonyDTO> getAllTestimonies(Pageable pageable) {
        return testimonyRepository.findAll(pageable).map(TestimonyDTO::fromEntity);
    }

    @Override
    public List<TestimonyDTO> getAllTheTestimonies() {
        return testimonyRepository.findAll().stream()
                .map(TestimonyDTO::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public Page<TestimonyDTO> getTestimoniesByStatus(Testimony.TestimonyStatus status, Pageable pageable) {
        return testimonyRepository.findByStatus(status, pageable).map(TestimonyDTO::fromEntity);
    }

    @Override
    public Page<TestimonyDTO> getTestimoniesByCategory(Testimony.TestimonyCategory category, Pageable pageable) {
        return testimonyRepository.findByCategory(category, pageable).map(TestimonyDTO::fromEntity);
    }

    @Override
    public Page<TestimonyDTO> getTestimoniesByUser(User userId, Pageable pageable) {
        User user = userRepository.findById(userId.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+userId));

        return testimonyRepository.findBySubmittedBy(user, pageable).map(TestimonyDTO::fromEntity);
    }

    @Override
    public Page<TestimonyDTO> getTestimoniesByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return testimonyRepository.findBySubmittedAtBetween(startDate, endDate, pageable).map(TestimonyDTO::fromEntity);
    }

    @Override
    public TestimonyDTO approveTestimony(Long id, User approver) {
        User user = userRepository.findById(approver.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with id: "+ approver));

        Testimony testimony = testimonyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: "+ id));
        testimony.setStatus(Testimony.TestimonyStatus.APPROVED);
        testimony.setApprovedBy(approver);
        testimony.setApprovedAt(LocalDateTime.now());

        return TestimonyDTO.fromEntity(testimonyRepository.save(testimony));
    }

    @Override
    public TestimonyDTO rejectTestimony(Long id, User rejector, String rejectionReason) {
        User approver = userRepository.findById(rejector.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with id: "+rejector));
        Testimony testimony = testimonyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: "+ id));

        testimony.setStatus(Testimony.TestimonyStatus.REJECTED);
        testimony.setApprovedBy(approver);
        testimony.setApprovedAt(LocalDateTime.now());
        testimony.setRejectionReason(rejectionReason);

        return TestimonyDTO.fromEntity(testimonyRepository.save(testimony));
    }

    @Override
    public TestimonyDTO likeTestimony(Long id, User userId) {
        User user = userRepository.findById(userId.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ userId));

        Testimony testimony = testimonyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: " +id));
        if (testimony.getLikedBy().add(user)){
            testimony.setLikesCount(testimony.getLikesCount() + 1);
        }

        return TestimonyDTO.fromEntity(testimonyRepository.save(testimony));
    }

    @Override
    public TestimonyDTO unlikeTestimony(Long id, User userId) {
        User user = userRepository.findById(userId.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ userId));

        Testimony testimony = testimonyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: " +id));
        if (testimony.getLikedBy().add(user)){
            testimony.setLikesCount(testimony.getLikesCount() - 1);
        }

        return TestimonyDTO.fromEntity(testimonyRepository.save(testimony));
    }

    @Override
    public long countTestimoniesByStatus(Testimony.TestimonyStatus status) {
        return testimonyRepository.countByStatus(status);
    }

    @Override
    public long countTestimoniesByCategory(Testimony.TestimonyCategory category) {
        return testimonyRepository.countByCategory(category);
    }

    @Override
    public TestimonyDTO shareTestimony(Long id) {
        Testimony testimony = testimonyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: " +id));
        testimony.setSharesCount(testimony.getSharesCount() +1);
        return TestimonyDTO.fromEntity(testimonyRepository.save(testimony));
    }

    @Override
    public boolean existsById(Long id) {
        return testimonyRepository.existsById(id);
    }
//    @Override
    public Page<TestimonyDTO> getTestimoniesByStatusAndCategory(Testimony.TestimonyStatus status,
                                                               Testimony.TestimonyCategory category,
                                                               Pageable pageable) {
        return testimonyRepository.findByStatusAndCategory(status, category, pageable).map(TestimonyDTO::fromEntity);
    }

//    @Override
    public Page<TestimonyDTO> getTestimoniesByApprover(Long approverId, Pageable pageable) {
        User approver = userRepository.findById(approverId)
            .orElseThrow(() -> new ResourceNotFoundException("Approver not found with id: " + approverId));
        return testimonyRepository.findByApprovedBy(approver, pageable).map(TestimonyDTO::fromEntity);
    }





//    @Override
    public List<TestimonyDTO> getApprovedTestimonies() {
        return testimonyRepository.findByStatus(Testimony.TestimonyStatus.APPROVED).stream()
                .map(TestimonyDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
    public List<TestimonyDTO> getPendingTestimonies() {
        return testimonyRepository.findByStatus(Testimony.TestimonyStatus.PENDING).stream()
                .map(TestimonyDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
    public List<TestimonyDTO> getRejectedTestimonies() {
        return testimonyRepository.findByStatus(Testimony.TestimonyStatus.REJECTED).stream()
                .map(TestimonyDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
    public List<TestimonyDTO> getTestimoniesByUser(User user) {
        return testimonyRepository.findBySubmittedBy(user).stream()
                .map(TestimonyDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
    public List<TestimonyDTO> getTestimoniesByCategory(Testimony.TestimonyCategory category) {
        return testimonyRepository.findByCategory(category).stream()
                .map(TestimonyDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
    public List<TestimonyDTO> getAnonymousTestimonies() {
        return testimonyRepository.findByIsAnonymous(true).stream()
                .map(TestimonyDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
    public List<TestimonyDTO> getMostLikedTestimonies(int limit) {
        return testimonyRepository.findByLikesCountGreaterThanEqual(limit).stream()
                .map(TestimonyDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
    public List<TestimonyDTO> getMostSharedTestimonies(int limit) {
        return testimonyRepository.findBySharesCountGreaterThanEqual(limit).stream()
                .map(TestimonyDTO::fromEntity)
                .collect(Collectors.toList());
    }


} 