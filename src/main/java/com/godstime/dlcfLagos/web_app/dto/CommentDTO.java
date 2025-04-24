package com.godstime.dlcfLagos.web_app.dto;

import com.godstime.dlcfLagos.web_app.model.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    
    private Long id;
    
    @NotBlank(message = "Content is required")
    @Size(max = 2000, message = "Content must not exceed 2000 characters")
    private String content;
    
    @NotNull(message = "Discussion ID is required")
    private Long discussionId;
    
    private Long userId;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private boolean isEdited;
    
    private int likesCount;
    
    public static CommentDTO fromEntity(Comment entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setDiscussionId(entity.getDiscussion().getId());
        dto.setUserId(entity.getUser().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setEdited(entity.isEdited());
        dto.setLikesCount(entity.getLikesCount());
        return dto;
    }
} 