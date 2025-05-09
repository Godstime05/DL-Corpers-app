package com.godstime.dlcfLagos.web_app.dto;

import com.godstime.dlcfLagos.web_app.models.Feedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FeedbackDTO {
    
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    @Size(max = 2000, message = "Content must not exceed 2000 characters")
    private String content;
    
    private Long submittedById;
    
    private LocalDateTime submittedAt;
    
    private LocalDateTime updatedAt;
    
    private boolean isAnonymous;
    
    @NotNull(message = "Feedback type is required")
    private Feedback.FeedbackType type;
    
    private Feedback.FeedbackStatus status;
    
    private Feedback.FeedbackPriority priority;
    
    private String response;
    
    private Long respondedById;
    
    private LocalDateTime respondedAt;
    
    private String resolutionNotes;
    
    public static FeedbackDTO fromEntity(Feedback entity) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setSubmittedById(entity.getSubmittedBy().getId());
        dto.setSubmittedAt(entity.getSubmittedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setAnonymous(entity.isAnonymous());
        dto.setType(entity.getType());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        dto.setResponse(entity.getResponse());
        if (entity.getRespondedBy() != null) {
            dto.setRespondedById(entity.getRespondedBy().getId());
        }
        dto.setRespondedAt(entity.getRespondedAt());
        dto.setResolutionNotes(entity.getResolutionNotes());
        return dto;
    }
} 