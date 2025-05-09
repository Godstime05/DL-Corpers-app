package com.godstime.dlcfLagos.web_app.dto;

import com.godstime.dlcfLagos.web_app.models.Discussion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DiscussionDTO {
    
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    @Size(max = 5000, message = "Content must not exceed 5000 characters")
    private String content;
    
    private Long createdById;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @NotNull(message = "Category is required")
    private Discussion.DiscussionCategory category;
    
    private Discussion.DiscussionStatus status;
    
    private int viewsCount;
    
    private int commentsCount;
    
    private boolean isPinned;
    
    private boolean isLocked;
    
    private Set<Long> tagIds;
    
    private Set<Long> followerIds;
    
    public static DiscussionDTO fromEntity(Discussion entity) {
        DiscussionDTO dto = new DiscussionDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedById(entity.getCreatedBy().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCategory(entity.getCategory());
        dto.setStatus(entity.getStatus());
        dto.setViewsCount(entity.getViewsCount());
        dto.setCommentsCount(entity.getCommentsCount());
        dto.setPinned(entity.isPinned());
        dto.setLocked(entity.isLocked());
        dto.setTagIds(entity.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet()));
        dto.setFollowerIds(entity.getFollowers().stream()
                .map(User::getId)
                .collect(Collectors.toSet()));
        return dto;
    }
} 