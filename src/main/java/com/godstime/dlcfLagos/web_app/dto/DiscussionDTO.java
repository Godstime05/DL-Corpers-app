package com.godstime.dlcfLagos.web_app.dto;

import com.godstime.dlcfLagos.web_app.models.Discussion;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.models.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;

@Data
@NoArgsConstructor
public class DiscussionDTO {
    
    @Setter
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
//        if (entity == null) {
//            return null;
//        }
        
        DiscussionDTO dto = new DiscussionDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        
        if (entity.getCreatedBy() != null) {
            dto.setCreatedById(entity.getCreatedBy().getId());
        }
        
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCategory(entity.getCategory());
        dto.setStatus(entity.getStatus());
        dto.setViewsCount(entity.getViewsCount());
        dto.setCommentsCount(entity.getCommentsCount());
        dto.setPinned(entity.isPinned());
        dto.setLocked(entity.isLocked());
        
        dto.setTagIds(entity.getTags() != null ?
            entity.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet()) :
            Collections.emptySet());
            
        dto.setFollowerIds(entity.getFollowers() != null ?
            entity.getFollowers().stream()
                .map(User::getId)
                .collect(Collectors.toSet()) :
            Collections.emptySet());
            
        return dto;
    }
} 