package com.godstime.dlcfLagos.web_app.dto;

import com.godstime.dlcfLagos.web_app.models.PrayerRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PrayerRequestDTO {
    
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
    
    @NotNull(message = "Category is required")
    private PrayerRequest.PrayerCategory category;
    
    private PrayerRequest.PrayerStatus status;
    
    private int prayerCount;
    
    private String response;
    
    private Long respondedById;
    
    private LocalDateTime respondedAt;
    
    public static PrayerRequestDTO fromEntity(PrayerRequest entity) {
        PrayerRequestDTO dto = new PrayerRequestDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setSubmittedById(entity.getSubmittedBy().getId());
        dto.setSubmittedAt(entity.getSubmittedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setAnonymous(entity.isAnonymous());
        dto.setCategory(entity.getCategory());
        dto.setStatus(entity.getStatus());
        dto.setPrayerCount(entity.getPrayerCount());
        dto.setResponse(entity.getResponse());
        if (entity.getRespondedBy() != null) {
            dto.setRespondedById(entity.getRespondedBy().getId());
        }
        dto.setRespondedAt(entity.getRespondedAt());
        return dto;
    }
} 