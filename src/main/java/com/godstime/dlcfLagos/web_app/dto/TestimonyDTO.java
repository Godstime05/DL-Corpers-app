package com.godstime.dlcfLagos.web_app.dto;

import com.godstime.dlcfLagos.web_app.model.Testimony;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TestimonyDTO {
    
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    @Size(max = 5000, message = "Content must not exceed 5000 characters")
    private String content;
    
    private Long submittedById;
    
    private LocalDateTime submittedAt;
    
    private LocalDateTime updatedAt;
    
    private boolean isAnonymous;
    
    @NotNull(message = "Category is required")
    private Testimony.TestimonyCategory category;
    
    private Testimony.TestimonyStatus status;
    
    private String rejectionReason;
    
    private Long approvedById;
    
    private LocalDateTime approvedAt;
    
    private int likesCount;
    
    private int sharesCount;
    
    @Size(max = 100, message = "Location must not exceed 100 characters")
    private String location;
    
    @Size(max = 100, message = "Event date must not exceed 100 characters")
    private String eventDate;
    
    public static TestimonyDTO fromEntity(Testimony entity) {
        TestimonyDTO dto = new TestimonyDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setSubmittedById(entity.getSubmittedBy().getId());
        dto.setSubmittedAt(entity.getSubmittedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setAnonymous(entity.isAnonymous());
        dto.setCategory(entity.getCategory());
        dto.setStatus(entity.getStatus());
        dto.setRejectionReason(entity.getRejectionReason());
        if (entity.getApprovedBy() != null) {
            dto.setApprovedById(entity.getApprovedBy().getId());
        }
        dto.setApprovedAt(entity.getApprovedAt());
        dto.setLikesCount(entity.getLikesCount());
        dto.setSharesCount(entity.getSharesCount());
        dto.setLocation(entity.getLocation());
        dto.setEventDate(entity.getEventDate());
        return dto;
    }
} 