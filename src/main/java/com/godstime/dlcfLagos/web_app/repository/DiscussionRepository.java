package com.godstime.dlcfLagos.web_app.repository;

import com.godstime.dlcfLagos.web_app.model.Discussion;
import com.godstime.dlcfLagos.web_app.model.Tag;
import com.godstime.dlcfLagos.web_app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    
    List<Discussion> findByCreatedBy(User createdBy);
    
    List<Discussion> findByCategory(Discussion.DiscussionCategory category);
    
    List<Discussion> findByStatus(Discussion.DiscussionStatus status);
    
    List<Discussion> findByIsPinned(boolean isPinned);
    
    List<Discussion> findByIsLocked(boolean isLocked);
    
    List<Discussion> findByTagsContaining(Tag tag);
    
    List<Discussion> findByFollowersContaining(User user);
    
    List<Discussion> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Discussion> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Discussion> findByViewsCountGreaterThanEqual(int minViews);
    
    List<Discussion> findByCommentsCountGreaterThanEqual(int minComments);
    
    @Query("SELECT d FROM Discussion d WHERE d.title LIKE %:keyword% OR d.content LIKE %:keyword%")
    List<Discussion> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT d FROM Discussion d JOIN d.tags t WHERE t.id IN :tagIds GROUP BY d HAVING COUNT(t) = :tagCount")
    List<Discussion> findByTags(@Param("tagIds") List<Long> tagIds, @Param("tagCount") long tagCount);
    
    long countByCategory(Discussion.DiscussionCategory category);
    
    long countByStatus(Discussion.DiscussionStatus status);
    
    long countByCreatedBy(User createdBy);
    
    Page<Discussion> findAll(Pageable pageable);
} 