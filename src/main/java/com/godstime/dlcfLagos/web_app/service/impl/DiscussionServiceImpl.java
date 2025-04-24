package com.godstime.dlcfLagos.web_app.service.impl;

import com.godstime.dlcfLagos.web_app.dto.DiscussionDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.model.Discussion;
import com.godstime.dlcfLagos.web_app.model.Tag;
import com.godstime.dlcfLagos.web_app.model.User;
import com.godstime.dlcfLagos.web_app.repository.DiscussionRepository;
import com.godstime.dlcfLagos.web_app.repository.TagRepository;
import com.godstime.dlcfLagos.web_app.repository.UserRepository;
import com.godstime.dlcfLagos.web_app.service.DiscussionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiscussionServiceImpl implements DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public DiscussionDTO createDiscussion(DiscussionDTO discussionDTO, User createdBy) {
        Discussion discussion = new Discussion();
        mapDTOToEntity(discussionDTO, discussion);
        discussion.setCreatedBy(createdBy);
        discussion.setCreatedAt(LocalDateTime.now());
        discussion.setUpdatedAt(LocalDateTime.now());
        discussion.setStatus(Discussion.DiscussionStatus.ACTIVE);
        discussion.setViewsCount(0);
        discussion.setCommentsCount(0);
        discussion.setPinned(false);
        discussion.setLocked(false);
        
        if (discussionDTO.getTagIds() != null) {
            discussion.setTags(discussionDTO.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new EntityNotFoundException("Tag not found")))
                    .collect(Collectors.toSet()));
        }
        
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDTO updateDiscussion(Long id, DiscussionDTO discussionDTO, User updatedBy) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        if (!discussion.getCreatedBy().equals(updatedBy)) {
            throw new SecurityException("Only the creator can update the discussion");
        }
        
        mapDTOToEntity(discussionDTO, discussion);
        discussion.setUpdatedAt(LocalDateTime.now());
        
        if (discussionDTO.getTagIds() != null) {
            discussion.setTags(discussionDTO.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new EntityNotFoundException("Tag not found")))
                    .collect(Collectors.toSet()));
        }
        
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDTO closeDiscussion(Long id, User closedBy) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        discussion.setStatus(Discussion.DiscussionStatus.CLOSED);
        discussion.setUpdatedAt(LocalDateTime.now());
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDTO archiveDiscussion(Long id, User archivedBy) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        discussion.setStatus(Discussion.DiscussionStatus.ARCHIVED);
        discussion.setUpdatedAt(LocalDateTime.now());
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDTO pinDiscussion(Long id, User pinnedBy) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        discussion.setPinned(true);
        discussion.setUpdatedAt(LocalDateTime.now());
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDTO unpinDiscussion(Long id, User unpinnedBy) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        discussion.setPinned(false);
        discussion.setUpdatedAt(LocalDateTime.now());
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDTO lockDiscussion(Long id, User lockedBy) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        discussion.setLocked(true);
        discussion.setUpdatedAt(LocalDateTime.now());
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDTO unlockDiscussion(Long id, User unlockedBy) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        discussion.setLocked(false);
        discussion.setUpdatedAt(LocalDateTime.now());
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public void deleteDiscussion(Long id, User deletedBy) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        if (!discussion.getCreatedBy().equals(deletedBy)) {
            throw new SecurityException("Only the creator can delete the discussion");
        }
        
        discussionRepository.delete(discussion);
    }

    @Override
    @Transactional(readOnly = true)
    public DiscussionDTO getDiscussionById(Long id) {
        return discussionRepository.findById(id)
                .map(DiscussionDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getAllDiscussions() {
        return discussionRepository.findAll().stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getDiscussionsByUser(User user) {
        return discussionRepository.findByCreatedBy(user).stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getDiscussionsByCategory(Discussion.DiscussionCategory category) {
        return discussionRepository.findByCategory(category).stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getDiscussionsByStatus(Discussion.DiscussionStatus status) {
        return discussionRepository.findByStatus(status).stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getPinnedDiscussions() {
        return discussionRepository.findByIsPinned(true).stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getLockedDiscussions() {
        return discussionRepository.findByIsLocked(true).stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getDiscussionsByTags(List<Long> tagIds) {
        return discussionRepository.findByTags(tagIds, (long) tagIds.size()).stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getDiscussionsByKeyword(String keyword) {
        return discussionRepository.searchByKeyword(keyword).stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getMostViewedDiscussions(int limit) {
        return discussionRepository.findByViewsCountGreaterThanEqual(limit).stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscussionDTO> getMostCommentedDiscussions(int limit) {
        return discussionRepository.findByCommentsCountGreaterThanEqual(limit).stream()
                .map(DiscussionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public DiscussionDTO followDiscussion(Long id, User user) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        if (!discussion.getFollowers().contains(user)) {
            discussion.getFollowers().add(user);
            discussion.setUpdatedAt(LocalDateTime.now());
        }
        
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDTO unfollowDiscussion(Long id, User user) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        if (discussion.getFollowers().contains(user)) {
            discussion.getFollowers().remove(user);
            discussion.setUpdatedAt(LocalDateTime.now());
        }
        
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDTO incrementViews(Long id) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
        
        discussion.setViewsCount(discussion.getViewsCount() + 1);
        discussion.setUpdatedAt(LocalDateTime.now());
        return DiscussionDTO.fromEntity(discussionRepository.save(discussion));
    }

    @Override
    @Transactional(readOnly = true)
    public long countDiscussionsByCategory(Discussion.DiscussionCategory category) {
        return discussionRepository.countByCategory(category);
    }

    @Override
    @Transactional(readOnly = true)
    public long countDiscussionsByStatus(Discussion.DiscussionStatus status) {
        return discussionRepository.countByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return discussionRepository.existsById(id);
    }

    @Override
    public Discussion findDiscussionById(Long id) {
        return discussionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discussion not found with id: " + id));
    }

    private void mapDTOToEntity(DiscussionDTO dto, Discussion entity) {
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setCategory(dto.getCategory());
    }
} 