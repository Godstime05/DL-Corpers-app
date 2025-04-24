package com.godstime.dlcfLagos.web_app.service;

import com.godstime.dlcfLagos.web_app.dto.DiscussionDTO;
import com.godstime.dlcfLagos.web_app.model.Discussion;
import com.godstime.dlcfLagos.web_app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscussionService {
    
    DiscussionDTO createDiscussion(DiscussionDTO discussionDTO, User createdBy);
    
    DiscussionDTO updateDiscussion(Long id, DiscussionDTO discussionDTO, User updatedBy);
    
    DiscussionDTO closeDiscussion(Long id, User closedBy);
    
    DiscussionDTO archiveDiscussion(Long id, User archivedBy);
    
    DiscussionDTO pinDiscussion(Long id, User pinnedBy);
    
    DiscussionDTO unpinDiscussion(Long id, User unpinnedBy);
    
    DiscussionDTO lockDiscussion(Long id, User lockedBy);
    
    DiscussionDTO unlockDiscussion(Long id, User unlockedBy);
    
    void deleteDiscussion(Long id, User deletedBy);
    
    DiscussionDTO getDiscussionById(Long id);
    
    Discussion findDiscussionById(Long id);
    
    List<DiscussionDTO> getAllDiscussions();
    
    List<DiscussionDTO> getDiscussionsByUser(User user);
    
    List<DiscussionDTO> getDiscussionsByCategory(Discussion.DiscussionCategory category);
    
    List<DiscussionDTO> getDiscussionsByStatus(Discussion.DiscussionStatus status);
    
    List<DiscussionDTO> getPinnedDiscussions();
    
    List<DiscussionDTO> getLockedDiscussions();
    
    List<DiscussionDTO> getDiscussionsByTags(List<Long> tagIds);
    
    List<DiscussionDTO> getDiscussionsByKeyword(String keyword);
    
    List<DiscussionDTO> getMostViewedDiscussions(int limit);
    
    List<DiscussionDTO> getMostCommentedDiscussions(int limit);
    
    DiscussionDTO followDiscussion(Long id, User user);
    
    DiscussionDTO unfollowDiscussion(Long id, User user);
    
    DiscussionDTO incrementViews(Long id);
    
    long countDiscussionsByCategory(Discussion.DiscussionCategory category);
    
    long countDiscussionsByStatus(Discussion.DiscussionStatus status);
    
    boolean existsById(Long id);
} 